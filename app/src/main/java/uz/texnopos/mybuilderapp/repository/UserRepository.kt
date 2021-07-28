package uz.texnopos.mybuilderapp.repository

import uz.texnopos.mybuilderapp.network.RestApis

class UserRepository(private val api: RestApis) {
    fun getUserData(id: String) = api.getUserData(id)
}