package uz.texnopos.mybuilderapp.ui.main.profile.resume.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.texnopos.mybuilderapp.core.callApi
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.data.models.Country
import uz.texnopos.mybuilderapp.data.repository.Repository

class AddressViewModel(private val repo: Repository) : ViewModel() {
    private var _places = MutableLiveData<Resource<List<Country>>>()
    val places: LiveData<Resource<List<Country>>> get() = _places

 fun getPlaces() {
        _places.value = Resource.loading()
        viewModelScope.launch {
            callApi(repo.getPlaces(),
                onApiSuccess = {
                    _places.value = Resource.success(it!!.data)
                },
                onApiError = {
                    _places.value = Resource.error(it)
                })
        }

    }
}