package geoiss.model

import com.fasterxml.jackson.module.kotlin.readValue
import geoiss.config.CustomObjectMapper
import geoiss.model.geojson.Geometry
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DeserializationTest {

    @Test
    fun `Coordinate deserialization test`() {
        val jsonString = "[9.137248, 48.790411]"
        val expectedCoordinate = listOf(9.137248f, 48.790411f)
        val result = CustomObjectMapper.readValue<List<Float>>(jsonString)
        verifyCoordinate(expectedCoordinate, result)
    }

    @Test
    fun `Coordinate array deserialization test`() {
        val jsonString =
            """
        [[9.137248, 48.790411],
        [9.137248,48.790263],
        [9.13695,48.790263],
        [9.137248,48.790411]]
        """

        val expectedCoordinates = listOf(
            listOf(9.137248f, 48.790411f),
            listOf(9.137248f, 48.790263f),
            listOf(9.13695f, 48.790263f),
            listOf(9.137248f, 48.790411f)
        )

        val coordinates = CustomObjectMapper.readValue<List<List<Float>>>(jsonString)
        assertEquals(4, coordinates.size)
        coordinates.forEachIndexed { i, actual ->
            verifyCoordinate(expectedCoordinates[i], actual)
        }
    }

    @Test
    fun `Geometry deserialization test`() {
        val jsonString =
            """
        {
            "type": "Polygon",
            "coordinates": [
                [[9.137248, 48.790411],
                [9.137248,48.790263],
                [9.13695,48.790263],
                [9.137248,48.790411]]
            ]
        }
        """

        val expectedCoordinates = listOf(
            listOf(9.137248f, 48.790411f),
            listOf(9.137248f, 48.790263f),
            listOf(9.13695f, 48.790263f),
            listOf(9.137248f, 48.790411f)
        )

        val geometry = CustomObjectMapper.readValue<Geometry>(jsonString)
        assertEquals(4, geometry.coordinates().size)
        geometry.coordinates().forEachIndexed { i, actual ->
            verifyCoordinate(expectedCoordinates[i], actual)
        }
    }

    private fun verifyCoordinate(expected: List<Float>, actual: List<Float>) {
        assertEquals(2, actual.size)
        actual.forEachIndexed { i, value ->
            assertEquals(expected[i], value)
        }
    }
}
