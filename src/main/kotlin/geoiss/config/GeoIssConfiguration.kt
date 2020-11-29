package geoiss.config

import com.fasterxml.jackson.module.kotlin.readValue
import geoiss.model.geojson.CountriesGeoJson
import geoiss.model.geojson.GeoCountry
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.util.FileCopyUtils
import java.io.IOException
import java.io.InputStreamReader
import java.io.UncheckedIOException

@EnableScheduling
@Configuration
class GeoIssConfiguration {

//    @Bean
//    fun geoCities(
//        @Value("classpath:cities.geojson")
//        citiesGeoJson: Resource
//    ): List<GeoCity> = CustomObjectMapper.readValue<GeoCities>(citiesGeoJson.asString()).cities

    @Bean
    fun geoCountries(
        @Value("classpath:countries.geo.json")
        countriesGeoJsonResource: Resource
    ): List<GeoCountry> =
        CustomObjectMapper.readValue<CountriesGeoJson>(countriesGeoJsonResource.asString())
            .toGeoCountries()

    private fun Resource.asString() = try {
        val reader = InputStreamReader(this.inputStream, Charsets.UTF_8)
        FileCopyUtils.copyToString(reader)
    } catch (e: IOException) {
        throw UncheckedIOException(e)
    }
}
