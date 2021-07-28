package uz.texnopos.mybuilderapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var lat:Double=0.0,
    var long:Double=0.0,
    var cityName:String?="",
    var countryName:String?="",
    var stateName:String?=""
):Parcelable
