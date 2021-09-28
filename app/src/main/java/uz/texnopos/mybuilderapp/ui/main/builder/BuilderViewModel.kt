package uz.texnopos.mybuilderapp.ui.main.builder

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.ResumeModel

class BuilderViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    private val _resume = MutableLiveData<Resource<ResumeModel>>()
    val resume get() = _resume

    fun getSingleResume(userId: String, resumeId: String) {
        _resume.value = Resource.loading()
        firebaseHelper.getSingleResume(userId, resumeId,
            {
                _resume.value= Resource.success(it)
                Log.d(TAG, "getSingleResume: $it")
            },
            {
                _resume.value= Resource.error(it!!)
            })
    }
}