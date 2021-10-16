package uz.texnopos.mybuilderapp.data.repository

import uz.texnopos.mybuilderapp.data.retrofit.RestApis
import uz.texnopos.mybuilderapp.data.models.Feedback

class Repository(private val api: RestApis) {
    fun getPlaces() = api.getPlaces()
    fun getAllTrades(jobName:String) = api.getAllTrades(jobName)
    fun postFeedback(feedback: Feedback) =api.postFeedback(feedback)
    fun getSingleResume(userId:String,resumeId:String)=api.getSingleResume(userId, resumeId)
}