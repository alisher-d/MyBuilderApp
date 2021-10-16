package uz.texnopos.mybuilderapp.ui.main.homeIn

import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper
import uz.texnopos.mybuilderapp.data.models.JobModel

class HomeInViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    fun getJobs(
        onJobAdded: (JobModel) -> Unit,
        onJobModified: (JobModel) -> Unit,
        onJobRemoved: (String) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
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