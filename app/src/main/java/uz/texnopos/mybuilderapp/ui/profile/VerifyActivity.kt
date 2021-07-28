package uz.texnopos.mybuilderapp.ui.profile

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilderapp.base.AppBaseActivity
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.databinding.ActivityVerifyBinding
import uz.texnopos.mybuilderapp.core.toast

class VerifyActivity : AppBaseActivity() {
    private val auth = FirebaseAuth.getInstance()
    lateinit var bind: ActivityVerifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val storedVerificationId = intent.getStringExtra("storedVerificationId")!!
        val number = intent.getStringExtra(USER_PHONE_NUMBER)
        val phone = bind.phone
        phone.text = number


        bind.idOtp.doOnTextChanged { text, start, before, count ->
            if (text?.length == 6) {
                showProgress(true)
                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(storedVerificationId, text.toString().trim())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        showProgress(false)
                        if (task.isSuccessful) {
                            finish()
                        } else {
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                toast("Invalid OTP")
                            }
                        }
                    }
            }
        }

    }
}