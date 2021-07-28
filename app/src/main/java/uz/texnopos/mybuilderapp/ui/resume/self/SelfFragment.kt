package uz.texnopos.mybuilderapp.ui.resume.self

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilderapp.*
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.databinding.FragmentSelfBinding


class SelfFragment : BaseFragment(R.layout.fragment_self) {
    private lateinit var bind: FragmentSelfBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentSelfBinding.bind(view)
        navController = Navigation.findNavController(view)
        bind.apply {
            etDescription.setText(getDescription())
            btnSave.onClick {
                if (isNetworkAvailable()) {
                    if (validate()) {
                        hideSoftKeyboard()
                        showProgress()
//                        FirebaseHelper().updateBuilderDescription(etDescription.textToString(),
//                            {
//                                hideProgress()
//                                toast(it!!)
//                                navController.navigate(R.id.action_selfFragment_to_homeMainFragment)
//                            }, {
//                                hideProgress()
//                                toast(it!!)
//                            }
//                        )

                    }
                }
            }
        }
    }

    fun validate(): Boolean {
        return when {
            bind.etDescription.checkIsEmpty() -> {
                bind.etDescription.showError("Field Required")
                false
            }
            bind.etDescription.textToString().length < 100 -> {
                bind.etDescription.showError("Minimum 100 chars")
                false
            }
            else -> true
        }
    }
}