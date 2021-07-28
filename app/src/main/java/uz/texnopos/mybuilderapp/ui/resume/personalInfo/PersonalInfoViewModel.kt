package uz.texnopos.mybuilderapp.ui.resume.personalInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.UserModel2

class PersonalInfoViewModel(private val firebaseHelper: FirebaseHelper): ViewModel() {
    private var _updating: MutableLiveData<Resource<String>> = MutableLiveData()
    val updating: LiveData<Resource<String>> =_updating


  fun updateUserInfo(user: UserModel2){
        _updating.value= Resource.loading()
        firebaseHelper.updateUserInfo(user,
            {
                _updating.value= Resource.success(it)
            },
            {
                _updating.value= Resource.error(it)
            })
    }
}
