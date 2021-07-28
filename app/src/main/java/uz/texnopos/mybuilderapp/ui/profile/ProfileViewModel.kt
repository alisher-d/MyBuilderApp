package uz.texnopos.mybuilderapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.callApi
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.RequestModel
import uz.texnopos.mybuilderapp.data.models.UserModel
import uz.texnopos.mybuilderapp.data.models.UserModel2
import uz.texnopos.mybuilderapp.repository.UserRepository

class ProfileViewModel(private val repo: UserRepository) : ViewModel() {
    private var _userData: MutableLiveData<Resource<RequestModel<UserModel>?>> = MutableLiveData()
    val userData: LiveData<Resource<RequestModel<UserModel>?>> get() = _userData


    fun getUserData(id: String) {
        _userData.value = Resource.loading()
        callApi(repo.getUserData(id),
            onApiSucces = {
                _userData.value = Resource.success(it)
            },
            onApiError = {
                _userData.value = Resource.error(it)
            })
    }
}