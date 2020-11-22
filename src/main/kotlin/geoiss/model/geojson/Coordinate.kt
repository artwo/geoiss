package geoiss.model.geojson

data class Coordinate(
    val longitude: Float,
    val latitude: Float
) {

    /**
     * Convenience method to get longitude
     */
    fun x(): Float = longitude

    /**
     * Convenience method to get latitude
     */
    fun y(): Float = latitude
}
