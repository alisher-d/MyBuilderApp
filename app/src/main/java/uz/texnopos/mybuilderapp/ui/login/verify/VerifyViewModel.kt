package uz.texnopos.mybuilderapp.ui.login.verify

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilderapp.core.Constants
import uz.texnopos.mybuilderapp.core.Constants.USER_EXISTS
import uz.texnopos.mybuilderapp.core.getSharedPreferences
import uz.texnopos.mybuilderapp.data.AuthHelper
import uz.texnopos.mybuilderapp.data.Resource

class VerifyViewModel(private val authHelper: AuthHelper) : ViewModel() {
    private var _verify = MutableLiveData<Resource<String>>()
    val verify get() = _verify

    fun verifyCode(storedVerifId: String, code: String) {
        _verify.value = Resource.loading()
        val credential: PhoneAuthCredential =
            PhoneAuthProvider.getCredential(storedVerifId, code.trim())
        authHelper.phoneAuth(credential,
            {
                getUserData()
            },
            {
                _verify.value = Resource.error(it!!)
            })
    }

    private fun getUserData() {
        Log.d(Constants.TAG, "getUserData: isRunning")
        _verify.value = Resource.loading()
        authHelper.getUserData(
            {
                _verify.value = Resource.success("User found")
            },
            {
                _verify.value=Resource.error(it)
            }
        )
    }

}