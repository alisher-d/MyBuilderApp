package uz.texnopos.mybuilderapp.data.models

data class Country(
    val countryName: String,
    val stateList: List<State>
)