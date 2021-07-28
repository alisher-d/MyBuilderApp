package uz.texnopos.mybuilderapp.network

import retrofit2.Call
import retrofit2.http.*
import uz.texnopos.mybuilderapp.data.models.RequestModel
import uz.texnopos.mybuilderapp.data.models.UserModel
import uz.texnopos.mybuilderapp.data.models.UserModel2

interface RestApis {
    @GET("getUserData")
    fun getUserData(@Query("userId") userId:String): Call<RequestModel<UserModel>>
}