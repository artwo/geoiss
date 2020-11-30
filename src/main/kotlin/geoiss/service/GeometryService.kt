package geoiss.service

import geoiss.model.geojson.GeoCountry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GeometryService @Autowired constructor(
//    val geoCities: List<GeoCity>,
    val geoCountries: List<GeoCountry>,
)
