package uz.texnopos.mybuilderapp.ui.shortinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_FULLNAME
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.Constants.USER_EXISTS
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.core.getSharedPreferences
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper

class ShortInfoViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    private var _creating: MutableLiveData<Resource<String>> = MutableLiveData()
    val created get() = _creating

    fun addNewUser(user: Map<String, String>) {
        _creating.value = Resource.loading()
        firebaseHelper.addNewUser(user,
            {
                getSharedPreferences().setValue(USER_FULLNAME,user["fullname"])
                getSharedPreferences().setValue(USER_PHONE_NUMBER,user["phone"])
                getSharedPreferences().setValue(USER_EMAIL,user["email"])
                getSharedPreferences().setValue(USER_EXISTS, 1)
                _creating.value = Resource.success(it)
            },
            {
                _creating.value = Resource.error(it)
            })
    }
}