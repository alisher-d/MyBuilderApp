package uz.texnopos.mybuilderapp.data.models

data class JobModel(
    val jobId:String?=null,
    val image:String="",
    val name:String="",
    val trades:ArrayList<String> = arrayListOf()
)
