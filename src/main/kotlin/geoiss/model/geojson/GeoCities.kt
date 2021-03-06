package geoiss.model.geojson

import com.fasterxml.jackson.annotation.JsonProperty

data class GeoCities(
    @JsonProperty("features")
    val cities: List<GeoCity>
)
