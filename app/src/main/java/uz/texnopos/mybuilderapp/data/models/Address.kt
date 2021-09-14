package uz.texnopos.mybuilderapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var regionName: String?=null,
    var countryName: String?=null,
    var stateName: String?=null
):Parcelable
