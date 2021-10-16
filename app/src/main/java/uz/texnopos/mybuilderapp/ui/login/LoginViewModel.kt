package uz.texnopos.mybuilderapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthCredential
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.data.firebase.AuthHelper

class LoginViewModel(private val authHelper: AuthHelper) : ViewModel() {

    private var _exists: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val exists: LiveData<Resource<Boolean>> get() = _exists

    private fun getUserData() {
        Log.d(TAG, "getUserData: isRunning")
        _exists.value = Resource.loading()
        authHelper.getUserData(
            {
                _exists.value = Resource.success(true)
            },
            {
                _exists.value = Resource.error(it)
            }
        )
    }



    fun signIn(credential: PhoneAuthCredential) {
        Log.d(TAG, "signIn: isRunning")
        _exists.value = Resource.loading()
        authHelper.phoneAuth(credential,
            {
                getUserData()
            },
            {
                _exists.value = Resource.error(it)
            }
        )
    }

    fun authWithGoogle(idToken:String){
        authHelper.authWithGoogle(idToken,
            {
                getUserData()
            },
            {
                _exists.value = Resource.error(it)
            })
    }


    fun signInWithGoogle(email:String,password:String){
        _exists.value = Resource.loading()
            authHelper.signInWithGoogle(email,password,
                {
                    _exists.value = Resource.success(true)
                },
                {
                    _exists.value = Resource.error(it)
                })
    }


}