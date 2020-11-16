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

    @GetMapping(value = ["/cities/count"], produces = ["application/json"])
    fun getCitiesCount(): Int = geometryService.geoCities.size
}
