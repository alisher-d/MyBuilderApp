package uz.texnopos.mybuilderapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel2(
    var userId: String="",
    var fullname: String,
    var phone: String,
    var email: String
):Parcelable