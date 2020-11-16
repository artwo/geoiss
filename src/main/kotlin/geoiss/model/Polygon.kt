package geoiss.model

import geoiss.model.geojson.GeoPolygon
import kotlin.math.max
import kotlin.math.min

data class Polygon(
        val id: String,
        val coordinates: List<Coordinate>
) {
    companion object {
        private const val INFINITE = 180f // MAXIMUM value of longitude

        fun fromGeoPolygon(geoPolygon: GeoPolygon): Polygon = Polygon(
                geoPolygon.id,
                geoPolygon.geometry.coordinates()
                        .map { Coordinate(it[0], it[1]) }
        )

        fun fromGeoPolygons(geoPolygons: List<GeoPolygon>): List<Polygon> = geoPolygons.map { fromGeoPolygon(it) }
    }

    fun containsCoordinate(position: Coordinate): Boolean {
        val extreme = Coordinate(INFINITE, position.latitude)
        var intersectionCount = 0
        coordinates.forEachIndexed { i, p1 ->
            val next = (i + 1) % coordinates.size
            val q1 = coordinates[next]

            if (segmentsIntersect(p1, q1, position, extreme)) {
                if (calculateOrientation(p1, position, q1) == Orientation.COLLINEAR)
                    return onSegment(p1, position, q1)

                intersectionCount++
            }
        }
        return intersectionCount % 2 == 1
    }

    /**
     * Given Coordinates p, q, r, the function checks if point q lies on line segment 'pr'
     */
    private fun onSegment(p: Coordinate, q: Coordinate, r: Coordinate): Boolean =
            q.x() <= max(p.x(), r.x()) &&
                    q.x() >= min(p.x(), r.x()) &&
                    q.y() <= max(p.y(), r.y()) &&
                    q.y() >= min(p.y(), r.y())

    /**
     * Given Coordinates p, q, r, the function calculates the orientation of the segments.
     */
    private fun calculateOrientation(p: Coordinate, q: Coordinate, r: Coordinate): Orientation {
        val value = (q.y() - p.y()) * (r.x() - q.x()) - (q.x() - p.x()) * (r.y() - q.y())
        return when {
            value == 0f -> Orientation.COLLINEAR
            value > 0f -> Orientation.CLOCKWISE
            else -> Orientation.COUNTERCLOCKWISE
        }
    }

    /**
     * Given the segments p1q1, p2q2, the function determines whether they intersect.
     */
    private fun segmentsIntersect(p1: Coordinate, q1: Coordinate, p2: Coordinate, q2: Coordinate): Boolean {
        val o1 = calculateOrientation(p1, q1, p2)
        val o2 = calculateOrientation(p1, q1, q2)
        val o3 = calculateOrientation(p2, q2, p1)
        val o4 = calculateOrientation(p2, q2, q1)

        return when {
            // General case
            o1 != o2 && o3 != o4 -> true
            // Special cases
            o1 == Orientation.COLLINEAR && onSegment(p1, p2, q1) -> true
            o2 == Orientation.COLLINEAR && onSegment(p1, q2, q1) -> true
            o3 == Orientation.COLLINEAR && onSegment(p2, p1, q2) -> true
            o4 == Orientation.COLLINEAR && onSegment(p2, q1, q2) -> true
            else -> false
        }
    }
}
