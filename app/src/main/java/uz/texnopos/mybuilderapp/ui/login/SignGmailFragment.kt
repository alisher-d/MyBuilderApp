package uz.texnopos.mybuilderapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.core.checkIsEmpty
import uz.texnopos.mybuilderapp.core.onClick
import uz.texnopos.mybuilderapp.core.showError
import uz.texnopos.mybuilderapp.core.textToString
import uz.texnopos.mybuilderapp.databinding.PagerSignGmailBinding

class SignGmailFragment(private val mFragment: LoginFragment) :
    Fragment(R.layout.pager_sign_gmail) {
    private lateinit var bind: PagerSignGmailBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = PagerSignGmailBinding.bind(view)
        bind.apply {
            bind.btnLogin.onClick {
                if (validate()) {
                    mFragment.viewModel.signInWithGoogle(
                        etEmail.textToString(),
                        etPassword.textToString()
                    )
                }
            }
        }
    }
    private fun validate(): Boolean {
        return when {
            bind.etEmail.checkIsEmpty() -> {
                bind.etEmail.showError("Field Required")
                false
            }
            bind.etPassword.checkIsEmpty() -> {
                bind.etPassword.showError("Field Required")
                false
            }
            else -> true

        }
    }
}
