package geoiss.model.geojson

import com.fasterxml.jackson.annotation.JsonProperty

data class GeoCity(
    val properties: Properties,
    val geometry: PolygonGeometry
) {

    fun name(): String = properties.name

    data class Properties(
        @JsonProperty("NAME")
        val name: String
    )
}
