package geoiss.model.geojson

import com.fasterxml.jackson.annotation.JsonProperty

data class GeoPolygon(
    @JsonProperty("_id")
    val id: String,
    val geometry: Geometry
)
