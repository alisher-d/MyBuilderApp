package uz.texnopos.mybuilderapp.ui.resume.homeMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.ResumeModel

class ResumeViewModel(val firebaseHelper: FirebaseHelper): ViewModel() {
    private var _resumeSaved:MutableLiveData<Resource<String>> = MutableLiveData()
    val resumeSaved:LiveData<Resource<String>> =_resumeSaved


    fun setResume(resume: ResumeModel){
        _resumeSaved.value=Resource.loading()
        firebaseHelper.setResume(resume,
            {
                _resumeSaved.value=Resource.success(it)
            },
            {
                _resumeSaved.value=Resource.error(it)
            })
    }
}