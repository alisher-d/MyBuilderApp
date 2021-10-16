package uz.texnopos.mybuilderapp.ui.main.builder.feedback

import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper
import uz.texnopos.mybuilderapp.data.models.Feedback

class FeedbackViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    fun getAllFeedbacks(
        userId: String, resumeId: String,
        onAdded: (Feedback) -> Unit,
        onModified: (Feedback) -> Unit,
        onRemoved: (String) -> Unit,
        onFailure: (String?) -> Unit
    ){
        firebaseHelper.getAllFeedbacks(
            userId,resumeId,
            {
                onAdded.invoke(it)
            },
            {
                onModified.invoke(it)
            },
            {
                onRemoved.invoke(it)
            },
            {
                onFailure.invoke(it)
            }
        )
    }
}