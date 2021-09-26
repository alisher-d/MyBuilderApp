package uz.texnopos.mybuilderapp.ui.shortinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.core.Constants.USER_EXISTS
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.databinding.FragmentPersonalInfo0Binding
import uz.texnopos.mybuilderapp.ui.MainActivity

class ShortInfoFragment : BaseFragment(R.layout.fragment_personal_info_0) {
    lateinit var bind: FragmentPersonalInfo0Binding
    private val auth:FirebaseAuth by inject()
    lateinit var navController: NavController
    private val viewModel by viewModel<ShortInfoViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentPersonalInfo0Binding.bind(view)
        setUpObserves()
        bind.apply {
            val displayName = if (auth.currentUser!!.displayName != null)
                auth.currentUser!!.displayName
            else ""
            etFullName.setText(displayName)
            etPhone.setText(auth.currentUser!!.phoneNumber ?: "")
            etEmail.setText(auth.currentUser!!.email ?: "")
            navController = Navigation.findNavController(view)

            btnContinue.onClick {
                if (validate()) {
                    val user = mapOf(
                        "userId" to auth.currentUser!!.uid,
                        "fullname" to etFullName.textToString(),
                        "phone" to etPhone.textToString(),
                        "email" to etEmail.textToString()
                    )
                    viewModel.addNewUser(user)
                }
            }
        }
    }

    private fun validate(): Boolean {
        return when {
            bind.etFullName.checkIsEmpty() -> {
                bind.etFullName.showError("Field Required")
                false
            }
            bind.etPhone.checkIsEmpty() -> {
                bind.etPhone.showError("Field Required")
                false
            }
            bind.etEmail.checkIsEmpty() -> {
                bind.etEmail.showError("Field Required")
                false
            }
            else -> true

        }
    }

    override fun onStart() {
        super.onStart()
//        showNavBar(this,false)
    }
   private fun setUpObserves(){
        viewModel.created.observe(requireActivity(),{
            when(it.status){
                LoadingState.LOADING-> showProgress()
                LoadingState.SUCCESS-> {
                    hideProgress()
                    toast(it.data!!)
                    requireActivity().onBackPressed()
                }
                LoadingState.ERROR->{
                    hideProgress()
                    toast(it.message!!)
                }
            }
        })
    }
}