package uz.texnopos.mybuilderapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResumeModel(
    var userUID:String="",
    var resumeID:String="",
    var isCreated: Boolean = false,
    var isPublished:Boolean=true,
    var profession:String="",
    var trades:MutableList<String> = mutableListOf(),
    var address: Address? = null,
    var description: String = ""
):Parcelable