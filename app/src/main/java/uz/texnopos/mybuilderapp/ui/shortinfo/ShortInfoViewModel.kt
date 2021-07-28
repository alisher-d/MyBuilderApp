package uz.texnopos.mybuilderapp.ui.shortinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.UserModel
import uz.texnopos.mybuilderapp.data.models.UserModel2

class ShortInfoViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    private var _creating: MutableLiveData<Resource<String>> = MutableLiveData()
    val created: LiveData<Resource<String>> get() = _creating

    fun addNewUser(user: UserModel2) {
        _creating.value=Resource.loading()
        firebaseHelper.addNewUser(user,
            {
                _creating.value=Resource.success(it)
            },
            {
                _creating.value=Resource.error(it)
            })
    }

}