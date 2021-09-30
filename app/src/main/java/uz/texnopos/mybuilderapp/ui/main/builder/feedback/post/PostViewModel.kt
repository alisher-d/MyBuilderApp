package uz.texnopos.mybuilderapp.ui.main.builder.feedback.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.data.Resource
import uz.texnopos.mybuilderapp.data.models.Feedback

class PostViewModel(private val firebaseHelper: FirebaseHelper):ViewModel() {

    private var _feedback = MutableLiveData<Resource<String>>()
    val feedback get() = _feedback

    fun postFeedback(feedback: Feedback) {
        _feedback.value= Resource.loading()
        firebaseHelper.postFeedback(feedback,
            {
                _feedback.value = Resource.success(it)
            },
            {
                _feedback.value = Resource.error(it)
            })
    }

}