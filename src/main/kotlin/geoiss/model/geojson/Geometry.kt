package geoiss.model.geojson

interface Geometry {
    fun toPolygons(): List<Polygon>
}

data class AnyGeometry(
    val type: String,
    val coordinates: Any
) : Geometry {
    @Suppress("UNCHECKED_CAST")
    override fun toPolygons(): List<Polygon> =
        when (GeometryType.fromString(type)) {
            GeometryType.POLYGON -> PolygonGeometry(coordinates as List<List<List<Float>>>).toPolygons()
            GeometryType.MULTIPOLYGON -> MultiPolygonGeometry(coordinates as List<List<List<List<Float>>>>).toPolygons()
        }
}

data class PolygonGeometry(
    /**
     * Coordinates is a list of lists where only the first element is used
     */
    val coordinates: List<List<List<Float>>>
) : Geometry {

    fun coordinates(): List<List<Float>> = coordinates[0]

    private fun toPolygon(): Polygon = Polygon(
        coordinates[0].map {
            Coordinate(it[0], it[1])
        }
    )

    override fun toPolygons(): List<Polygon> = listOf(toPolygon())
}

data class MultiPolygonGeometry(
    val coordinates: List<List<List<List<Float>>>>
) : Geometry {

    override fun toPolygons(): List<Polygon> =
        coordinates.map { coordinates ->
            Polygon(
                coordinates[0].map { coordinate ->
                    Coordinate(coordinate[0], coordinate[1])
                }
            )
        }
}

enum class GeometryType(val value: String) {
    POLYGON("Polygon"),
    MULTIPOLYGON("MultiPolygon");

    companion object {
        fun fromString(s: String): GeometryType = when (s) {
            MULTIPOLYGON.value -> MULTIPOLYGON
            POLYGON.value -> POLYGON
            else -> throw IllegalArgumentException("Geometry type $s is not defined")
        }
    }
}
