package uz.texnopos.mybuilderapp.data.models

data class JobModel(
    val image:String="",
    val name:String="",
    val trades:ArrayList<String> = arrayListOf()
)
