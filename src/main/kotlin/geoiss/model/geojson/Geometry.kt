package geoiss.model.geojson

data class Geometry(
    val type: String,
    /**
     * Coordinates is a list of lists where only the first element is used
     */
    val coordinates: List<List<List<Float>>>
) {

    fun coordinates(): List<List<Float>> = coordinates[0]
}
