package geoiss.service

import geoiss.model.geojson.CountriesGeoJson
import geoiss.model.geojson.Polygon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GeometryService @Autowired constructor(
//    val geoCities: List<GeoCity>,
    val geoCountries: CountriesGeoJson,
    private val polygonsById: Map<String, Polygon>
) {

    fun polygonById(id: String): Polygon? = polygonsById[id]
}
