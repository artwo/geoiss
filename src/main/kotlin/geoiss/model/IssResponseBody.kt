package geoiss.model

import com.fasterxml.jackson.annotation.JsonProperty
import geoiss.model.geojson.Coordinate

data class IssResponseBody(
    @JsonProperty("iss_position")
    val issPosition: Coordinate
)
