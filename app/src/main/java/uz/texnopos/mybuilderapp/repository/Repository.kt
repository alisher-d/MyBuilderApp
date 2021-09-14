package uz.texnopos.mybuilderapp.repository

import uz.texnopos.mybuilderapp.network.RestApis

class Repository(private val api: RestApis) {
    fun getPlaces() = api.getPlaces()
}