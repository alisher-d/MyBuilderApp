package uz.texnopos.mybuilderapp.data.models

data class Country(
    val countryName: String,
    val stateList: List<State>
) {
    data class State(
        val regionList: List<Region>,
        val stateName: String
    ) {
        data class Region(
            val region: String
        )
    }
}