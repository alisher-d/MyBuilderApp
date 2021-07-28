package uz.texnopos.mybuilderapp.data.models

data class RequestModel<T>(
    val status: Boolean,
    val message: String,
    val data: T
)
