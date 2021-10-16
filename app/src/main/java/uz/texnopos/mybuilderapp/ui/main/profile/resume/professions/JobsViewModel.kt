package uz.texnopos.mybuilderapp.ui.main.profile.resume.professions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper
import uz.texnopos.mybuilderapp.data.models.JobModel

class JobsViewModel(private val firebaseHelper: FirebaseHelper):ViewModel() {
    private var _jobs:MutableLiveData<Resource<MutableList<JobModel>>> = MutableLiveData()
    val jobs=_jobs

    fun getJobs(){
        _jobs.value = Resource.loading()
        firebaseHelper.getJobs(
            {
                _jobs.value = Resource.success(it)
            },
            {
                _jobs.value = Resource.error(it)
            }
        )
    }
}