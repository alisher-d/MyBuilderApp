package uz.texnopos.mybuilderapp.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.RequestModel
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.data.models.UserModel

class ProfileViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {
    private var _userData: MutableLiveData<Resource<RequestModel<UserModel>?>> = MutableLiveData()
    val userData: LiveData<Resource<RequestModel<UserModel>?>> get() = _userData

    fun getUserResumes(
        onResumeAdded: (resume: ResumeModel) -> Unit,
        onResumeModified: (resume: ResumeModel) -> Unit,
        onResumeRemoved: (resumeId: String) -> Unit,
        onResumesSize:(Int)->Unit,
        onFailure: (msg: String?) -> Unit
    ){
        firebaseHelper.getUserResumes(
            {
                onResumeAdded.invoke(it)
            },
            {
                onResumeModified.invoke(it)
            },
            {
               onResumeRemoved.invoke(it)
            },
            {
                onResumesSize.invoke(it)
            },
            {
                onFailure.invoke(it!!)
            }
        )
    }
}