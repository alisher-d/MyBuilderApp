package uz.texnopos.mybuilderapp.data.models

data class Feedback(
    var id: String?=null,
    var authorId: String? = null,
    var receiverId: String? = null,
    var resumeId: String?=null,
    var rating: Int? = null,
    var text: String? = null,
    var createdTime: Long? = null
)
