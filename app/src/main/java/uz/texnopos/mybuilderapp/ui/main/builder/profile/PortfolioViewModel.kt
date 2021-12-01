package uz.texnopos.mybuilderapp.ui.main.builder.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.texnopos.mybuilderapp.core.Resource
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper
import uz.texnopos.mybuilderapp.data.models.ImageP

class PortfolioViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {

    fun getPortfolios(
        userId: String,
        resumeId: String,
        onImageAdded: (ImageP) -> Unit,
        onImageModified: (ImageP) -> Unit,
        onImageRemoved: (String) -> Unit,
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