package geoiss.controller

import geoiss.service.GeometryService
import geoiss.service.IssService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GeoIssController @Autowired constructor(
    private val geometryService: GeometryService,
    private val issService: IssService
) {

    @GetMapping(value = ["/iss"], produces = ["application/json"])
    fun getIssLocation() {
    }

//    @GetMapping(value = ["/cities/count"], produces = ["application/json"])
//    fun getCitiesCount(): Int = geometryService.geoCities.size

    @GetMapping(value = ["/countries/count"], produces = ["application/json"])
    fun getCountriesCount(): Int = geometryService.geoCountries.size

//    @GetMapping(value = ["/countries/geometry/types"], produces = ["application/json"])
//    fun getCountriesGeometryTypes(): List<GeometryType> = geometryService.geoCountries.countries.map {
//        GeometryType.fromString(it.geometry.type)
//    }
}
