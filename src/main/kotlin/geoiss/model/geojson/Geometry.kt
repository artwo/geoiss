package geoiss.model.geojson

data class AnyGeometry(
    val type: String,
    val coordinates: Any
)

data class PolygonGeometry(
    /**
     * Coordinates is a list of lists where only the first element is used
     */
    val coordinates: List<List<List<Float>>>
) {

    fun coordinates(): List<List<Float>> = coordinates[0]

    fun toPolygon(): Polygon = Polygon(
        coordinates[0].map {
            Coordinate(it[0], it[1])
        }
    )
}

// data class GeoPolygon(
//        @JsonProperty("_id")
//        val id: String,
//        val geometry: Geometry
// )


data class MultiPolygonGeometry(
    val coordinates: List<List<List<List<String>>>>
) {
//    fun toPolygons(): List<Polygon> =
//        coordinates.map { coordinates ->
//            Polygon(
//                coordinates[0].map { coordinate ->
//                    Coordinate(coordinate[0], coordinate[1])
//                }
//            )
//        }
}
