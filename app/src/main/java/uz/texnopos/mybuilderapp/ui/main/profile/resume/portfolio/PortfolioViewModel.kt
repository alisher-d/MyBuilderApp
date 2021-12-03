package uz.texnopos.mybuilderapp.ui.main.profile.resume.portfolio

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper
import uz.texnopos.mybuilderapp.data.models.ImageP

class PortfolioViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    private val _uploadTask = MutableLiveData<Resource<Any>>()
    val uploadTask get() = _uploadTask

    private val _deleteTask = MutableLiveData<Resource<Any>>()
    val deleteTask get() = _deleteTask

    fun deleteImage(imageP: ImageP) {
        _deleteTask.value = Resource.loading()
        firebaseHelper.deleteImage(
            imageP = imageP,
            onSuccess = {
                _deleteTask.value= Resource.success(it)
            },
            onFailure = {
                _deleteTask.value= Resource.error(it)
            }
        )
    }

    fun uploadImage(
        uri: Uri,
        imageP: ImageP,
    ) {
        _uploadTask.value = Resource.loading()
        firebaseHelper.uploadImage(
            uri = uri,
            imageP = imageP,
            onSuccess = {
                _uploadTask.value = Resource.success(it)
            },
            onFailure = {
                _uploadTask.value = Resource.error(it)
            }
        )
    }

    fun getPortfolios(
        userId: String,
        resumeId: String,
        onImageAdded: (ImageP) -> Unit,
        onImageModified: (ImageP) -> Unit,
        onImageRemoved: (ImageP) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        firebaseHelper.getAllPictures(
            userId = userId,
            resumeId = resumeId,
            onAdded = {
                onImageAdded.invoke(it)
            },
            onModified = {
                onImageModified.invoke(it)
            },
            onRemoved = {
                onImageRemoved.invoke(it)
            },
            onFailure = {
                onFailure.invoke(it!!)
            }
        )
    }



}