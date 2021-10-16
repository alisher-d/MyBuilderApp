package uz.texnopos.mybuilderapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResumeModel(
    var userId: String? = null,
    var resumeId: String? = null,
    var createdTime: Long? = null,
    var updatedTime: Long? = null,
    var isPublished: Boolean = true,
    var profession: String? = null,
    var trades: MutableList<String>? =null,
    var address: Address? = null,
    var description: String? = null,
    var rating:Float?= null,
    var feedbackCount:Int?=null
):Parcelable