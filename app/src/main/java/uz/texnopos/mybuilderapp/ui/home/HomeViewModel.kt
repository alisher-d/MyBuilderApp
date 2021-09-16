package uz.texnopos.mybuilderapp.ui.home

import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.models.JobModel

class HomeViewModel(private val firebaseHelper: FirebaseHelper):ViewModel() {

    fun getJobs(
        onJobAdded: (JobModel) -> Unit,
        onJobModified: (JobModel) -> Unit,
        onJobRemoved: (String) -> Unit,
        onFailure: (String?) -> Unit
    ){
        firebaseHelper.getJobs(
            {
                onJobAdded.invoke(it)
            },
            {
                onJobModified.invoke(it)
            },
            {
                onJobRemoved.invoke(it)
            },
            {
                onFailure.invoke(it)
            }
        )
    }
}