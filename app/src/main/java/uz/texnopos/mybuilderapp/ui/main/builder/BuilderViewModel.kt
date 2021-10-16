package uz.texnopos.mybuilderapp.ui.main.builder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.callApi
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.data.repository.Repository

class BuilderViewModel(private val repo: Repository) : ViewModel() {

    private val _resume = MutableLiveData<Resource<ResumeModel>>()
    val resume get() = _resume

    fun getSingleResume(userId: String, resumeId: String) {
        _resume.value = Resource.loading()
        callApi(repo.getSingleResume(userId, resumeId),
            {
                _resume.value = Resource.success(it!!.data)
            },
            {
                _resume.value = Resource.error(it)
            })
    }
}