package uz.texnopos.mybuilderapp.ui.login.verify

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.Constants.STORED_VERIFICATION_ID
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.hideProgress
import uz.texnopos.mybuilderapp.core.showProgress
import uz.texnopos.mybuilderapp.core.toast
import uz.texnopos.mybuilderapp.core.LoadingState
import uz.texnopos.mybuilderapp.databinding.FragmentVerifyBinding

class VerifyFragment : Fragment(R.layout.fragment_verify) {
    private val viewModel by viewModel<VerifyViewModel>()
    lateinit var bind: FragmentVerifyBinding
    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        navController = Navigation.findNavController(view)
        bind = FragmentVerifyBinding.bind(view)
        val storedVerificationId = arguments?.getString(STORED_VERIFICATION_ID)!!
        val phoneNumber = arguments?.getString(USER_PHONE_NUMBER)
        bind.apply {
            phone.text=phoneNumber
            idOtp.doOnTextChanged { text, _, _, _ ->
                if (text?.length == 6) {
                    viewModel.verifyCode(storedVerificationId, text.toString())
                }
            }
        }
    }
    private fun setUpObserver(){
        viewModel.verify.observe(viewLifecycleOwner,{
            when(it.status){
                LoadingState.LOADING-> showProgress()
                LoadingState.SUCCESS->{
                    requireActivity().onBackPressed()
                    hideProgress()
                }
                LoadingState.ERROR->{
                    toast(it.message!!)
                    hideProgress()
                }
            }
        })
    }
}