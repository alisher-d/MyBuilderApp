package uz.texnopos.mybuilderapp.data

import retrofit2.Call
import retrofit2.http.*
import uz.texnopos.mybuilderapp.data.models.*

interface RestApis {

    @GET("getPlaces")
    fun getPlaces():Call<RequestModel<List<Country>>>

    @GET("getAllTrades")
    fun getAllTrades(@Query("jobName") jobName:String):Call<RequestModel<List<TradeModel>>>
}