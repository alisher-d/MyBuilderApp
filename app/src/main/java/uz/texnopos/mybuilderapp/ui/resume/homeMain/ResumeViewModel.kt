package uz.texnopos.mybuilderapp.ui.resume.homeMain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.ResumeModel

class ResumeViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    private var _request = MutableLiveData<Resource<String>>()
    val request get() = _request

    fun setResume(resume: ResumeModel) {
        _request.value = Resource.loading()
        firebaseHelper.setResume(resume,
            {
                _request.value = Resource.success(it)
            },
            {
                _request.value = Resource.error(it)
            })
    }

    fun removeResume(
        resumeId: String,
    ) {
        _request.value = Resource.loading()
        firebaseHelper.removeResume(resumeId,
            {
                _request.value = Resource.success(it)
            },
            {
                _request.value = Resource.error(it)
            }
        )
    }
}