package uz.texnopos.mybuilderapp.ui.main.builder.feedback.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.callApi
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.data.models.Feedback
import uz.texnopos.mybuilderapp.data.repository.Repository

class PostViewModel(private val repo: Repository) : ViewModel() {

    private var _feedback = MutableLiveData<Resource<String>>()
    val feedback get() = _feedback

    fun postFeedback(feedback: Feedback) {
        _feedback.value = Resource.loading()
        callApi(repo.postFeedback(feedback),
            {
                _feedback.value = Resource.success(it!!.message)
            },
            {
                _feedback.value = Resource.error(it)
            })
    }

}