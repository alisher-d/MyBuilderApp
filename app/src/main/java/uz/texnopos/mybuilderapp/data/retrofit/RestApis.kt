package uz.texnopos.mybuilderapp.data.retrofit

import retrofit2.Call
import retrofit2.http.*
import uz.texnopos.mybuilderapp.data.models.*

interface RestApis {

    @GET("api/getPlaces")
    fun getPlaces():Call<RequestModel<List<Country>>>

    @GET("api/getAllTrades")
    fun getAllTrades(@Query("jobName") jobName:String):Call<RequestModel<List<TradeModel>>>

    @POST("api/postFeedback")
    fun postFeedback(@Body feedback: Feedback):Call<RequestModel<Any>>

    @GET("api/getSingleResume")
    fun getSingleResume(
        @Query("userId") userId:String,
        @Query("resumeId") resumeId:String ):Call<RequestModel<ResumeModel>>
}