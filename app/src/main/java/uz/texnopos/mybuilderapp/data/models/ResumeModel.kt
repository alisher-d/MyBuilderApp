package uz.texnopos.mybuilderapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResumeModel(
    var userUID: String? = null,
    var resumeID: String? = null,
    var createdTime: Long? = null,
    var updatedTime: Long? = null,
    var isPublished: Boolean = true,
    var profession: String? = null,
    var trades: MutableList<String>? =null,
    var address: Address? = null,
    var description: String? = null
):Parcelable