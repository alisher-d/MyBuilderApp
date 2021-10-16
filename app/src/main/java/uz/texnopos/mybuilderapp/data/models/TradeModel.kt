package uz.texnopos.mybuilderapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TradeModel(
    val userId: String,
    val address: Address,
    val description: String,
    val email: String,
    val fullname: String,
    val phone: String,
    val resumeId: String
):Parcelable