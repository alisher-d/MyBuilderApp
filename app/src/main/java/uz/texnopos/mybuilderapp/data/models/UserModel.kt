package uz.texnopos.mybuilderapp.data.models


data class UserModel(
    var userId: String = "",
    var fullname: String = "",
    var phone: String = "",
    var email: String = "",
    var resumes: MutableList<ResumeModel> = mutableListOf()
)
