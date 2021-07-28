package uz.texnopos.mybuilderapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilderapp.ui.profile.VerifyActivity
import uz.texnopos.mybuilderapp.*
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.databinding.PagerSignPhoneBinding
import java.util.concurrent.TimeUnit

class SignPhoneFragment(val parentFragment: LoginFragment) : BaseFragment(R.layout.pager_sign_phone) {
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var bind: PagerSignPhoneBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = PagerSignPhoneBinding.bind(view)
        bind.apply {
            etPhone.doOnTextChanged { text, _, _, _ ->
                when {
                    text!!.isEmpty() -> btnGetCode.isEnabled = false
                    text.length == 9 -> {
                        etPhone.hideSoftKeyboard()
                        btnGetCode.isEnabled = true
                    }
                    else -> {
                        inputPhone.error = null
                        btnGetCode.isEnabled = false
                    }
                }
                btnGetCode.onClick {
                    if (isNetworkAvailable()) {
                        val number = etPhone.textToString().trim()
                        sendVerificationcode("+998$number")
                        parentFragment.showProgress()
                    }
                }
            }
            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    parentFragment.viewModel.signIn(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    toast(e.message!!)
                    parentFragment.hideProgress()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    storedVerificationId = verificationId
                    resendToken = token
                    val intent = Intent(context, VerifyActivity::class.java)
                    intent.putExtra(USER_PHONE_NUMBER, "+998${bind.etPhone.textToString()}")
                    intent.putExtra("storedVerificationId", storedVerificationId)
                    startActivity(intent)
                    hideProgress()
                }
            }
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(parentFragment.auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
