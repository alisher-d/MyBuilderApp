package uz.texnopos.mybuilderapp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.core.Constants.STORED_VERIFICATION_ID
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.databinding.PagerSignPhoneBinding
import java.util.concurrent.TimeUnit

class SignPhoneFragment(val parentFragment: LoginFragment) : Fragment(R.layout.pager_sign_phone) {
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var bind: PagerSignPhoneBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = PagerSignPhoneBinding.bind(view)
        bind.apply {

            etPhone.addTextChangedListener(MaskWatcher.phoneNumber())
            etPhone.doOnTextChanged { text, _, _, _ ->
                when {
                    text!!.isEmpty() -> btnGetCode.isEnabled = false
                    text.length == 14 -> {
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
                        val number = etPhone.textToString().filter { it.isDigit() }
                        sendVerificationCode("+998$number")
                        showProgress()
                    }
                }
            }
            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted: $credential")
                    parentFragment.viewModel.signIn(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    toast(e.message!!)
                    hideProgress()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Log.d(TAG, "onCodeSent: $verificationId token: $token")
                    val bundle = Bundle().apply {
                        putString(STORED_VERIFICATION_ID, verificationId)
                        putString(USER_PHONE_NUMBER, "+998${bind.etPhone.textToString()}")
                    }
                    parentFragment.navController.navigate(
                        R.id.action_loginFragment_to_verifyFragment,
                        bundle
                    )
                    hideProgress()
                }
            }
        }
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(parentFragment.auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}



