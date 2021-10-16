package uz.texnopos.mybuilderapp.ui.main.profile

import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper
import uz.texnopos.mybuilderapp.data.models.ResumeModel

class ProfileViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

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