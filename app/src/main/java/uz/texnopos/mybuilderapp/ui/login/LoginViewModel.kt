package uz.texnopos.mybuilderapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthCredential
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_FULL_NAME
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.Constants.USER_EXISTS
import uz.texnopos.mybuilderapp.core.getSharedPreferences
import uz.texnopos.mybuilderapp.data.AuthHelper
import uz.texnopos.mybuilderapp.data.Resource

class LoginViewModel(private val authHelper: AuthHelper) : ViewModel() {

    private var _exists: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val exists: LiveData<Resource<Boolean>> get() = _exists

    private var _registration: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val registration: LiveData<Resource<Boolean>>
        get() = _registration

    private var _condition: MutableLiveData<Int> = MutableLiveData()
    val condition: LiveData<Int> get() = _condition
    fun getUserData() {
        _exists.value = Resource.loading()
        authHelper.getUserData(
            {
              if (it!=null)  {
                  _exists.value=Resource.success(true)
                  getSharedPreferences().setValue(USER_FULL_NAME,it.fullname)
                  getSharedPreferences().setValue(USER_PHONE_NUMBER,it.phone)
                  getSharedPreferences().setValue(USER_EMAIL,it.email)
              }
                else _exists.value=Resource.success(false)
            },
            {
                _exists.value=Resource.error(it)
            }
        )
    }



    fun signIn(credential: PhoneAuthCredential) {
        _registration.value = Resource.loading()
        authHelper.phoneAuth(credential,
            {
                _registration.value = Resource.success(it)
            },
            {
                _registration.value = Resource.error(it)
            }
        )
    }
    fun updateUI() {
        _condition.value = getSharedPreferences().getIntValue(USER_EXISTS, 0)
    }
    fun authWithGoogle(idToken:String){
        authHelper.authWithGoogle(idToken,
            {
                _registration.value= Resource.success(it)
            },
            {
                _registration.value=Resource.error(it)
            })
    }
    fun signInWithGoogle(email:String,password:String){
        _registration.value=Resource.loading()
            authHelper.signInWithGoogle(email,password,
                {
                    _registration.value=Resource.success(true)
                },
                {
                    _registration.value=Resource.error(it)
                })
    }
}