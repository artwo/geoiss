package geoiss.service

import geoiss.model.GeoCity
import geoiss.model.Polygon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GeometryService @Autowired constructor(
    val geoCities: List<GeoCity>,
    private val polygonsById: Map<String, Polygon>
) {

    fun polygonById(id: String): Polygon? = polygonsById[id]
}
