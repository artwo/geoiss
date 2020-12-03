package geoiss.service

import geoiss.model.geojson.GeoCountry
import org.springframework.stereotype.Service

@Service
class GeometryService(
//    val geoCities: List<GeoCity>,
    val geoCountries: List<GeoCountry>,
)
