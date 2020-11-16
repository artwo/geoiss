package geoiss.model

import geoiss.model.geojson.Geometry
import geoiss.model.geojson.Properties

data class GeoCity(
    val type: String,
    val properties: Properties,
    val geometry: Geometry
) {

    fun name(): String = properties.name
}
