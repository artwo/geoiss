package geoiss.model.geojson

import com.fasterxml.jackson.annotation.JsonProperty

data class CountriesGeoJson(
    @JsonProperty("features")
    val countries: List<CountryGeoJson>
) {
//    fun toGeoCountries(): List<GeoCountry> = countries.map { it.toGeoCountry() }
}

data class CountryGeoJson(
    val properties: Properties,
    val geometry: AnyGeometry
) {
    data class Properties(
        @JsonProperty("geounit")
        val geoUnit: String,
        val name: String,
        @JsonProperty("name_long")
        val nameLong: String,
        @JsonProperty("name_sort")
        val nameSort: String
    )

//    fun toGeoCountry(): GeoCountry = GeoCountry(
//        geoUnit = properties.geoUnit,
//        name = properties.name,
//        nameLong = properties.nameLong,
//        nameSort = properties.nameSort,
//        polygons = geometry.toPolygons()
//    )
}

data class GeoCountry(
    val geoUnit: String,
    val name: String,
    val nameLong: String,
    val nameSort: String,
    val polygons: List<Polygon>
)