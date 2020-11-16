package geoiss.model.geojson

import com.fasterxml.jackson.annotation.JsonProperty

data class Properties(
    @JsonProperty("NAME")
    val name: String
)
