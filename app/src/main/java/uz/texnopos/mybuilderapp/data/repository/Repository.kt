package uz.texnopos.mybuilderapp.data.repository

import uz.texnopos.mybuilderapp.data.RestApis

class Repository(private val api: RestApis) {
    fun getPlaces() = api.getPlaces()
}