package geoiss.model

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PolygonTest {

    @Test
    fun `Simple Polygon test with point inside `() {
        val a = Coordinate(3f, 1f)
        val b = Coordinate(4f, 3f)
        val c = Coordinate(6f, 1f)
        val d = Coordinate(3f, 1f)
        val p = Polygon("1234", listOf(a, b, c, d))

        val point = Coordinate(4.2f, 1.97f)

        assertTrue(p.containsCoordinate(point))
    }

    @Test
    fun `Simple Polygon test with point outside on the left`() {
        val a = Coordinate(3f, 1f)
        val b = Coordinate(4f, 3f)
        val c = Coordinate(6f, 1f)
        val d = Coordinate(3f, 1f)
        val p = Polygon("1234", listOf(a, b, c, d))

        val point = Coordinate(2.58f, 2.51f)

        assertFalse(p.containsCoordinate(point))
    }

    @Test
    fun `Simple Polygon test with point outside on the right`() {
        val a = Coordinate(3f, 1f)
        val b = Coordinate(4f, 3f)
        val c = Coordinate(6f, 1f)
        val d = Coordinate(3f, 1f)
        val p = Polygon("1234", listOf(a, b, c, d))

        val point = Coordinate(5.96f, 2.15f)

        assertFalse(p.containsCoordinate(point))
    }

    @Test
    fun `Simple polygon test with point outside at the top`() {
        val a = Coordinate(3f, 1f)
        val b = Coordinate(4f, 3f)
        val c = Coordinate(6f, 1f)
        val d = Coordinate(3f, 1f)
        val p = Polygon("1234", listOf(a, b, c, d))

        val point = Coordinate(4f, 4f)

        assertFalse(p.containsCoordinate(point))
    }

    @Test
    fun `Simple Polygon test with point close but still outside`() {
        val a = Coordinate(3f, 1f)
        val b = Coordinate(4f, 3f)
        val c = Coordinate(6f, 1f)
        val d = Coordinate(3f, 1f)
        val p = Polygon("1234", listOf(a, b, c, d))

        val point = Coordinate(4.18f, 0.87f)

        assertFalse(p.containsCoordinate(point))
    }

    @Test
    fun `Simple Polygon test with point on top of segment`() {
        val a = Coordinate(3f, 1f)
        val b = Coordinate(4f, 3f)
        val c = Coordinate(6f, 1f)
        val d = Coordinate(3f, 1f)
        val p = Polygon("1234", listOf(a, b, c, d))

        val point = Coordinate(4.64f, 1f)

        assertTrue(p.containsCoordinate(point))
    }

    @Test
    fun `Simple Polygon test with point on top of a vertice`() {
        val a = Coordinate(3f, 1f)
        val b = Coordinate(4f, 3f)
        val c = Coordinate(6f, 1f)
        val d = Coordinate(3f, 1f)
        val p = Polygon("1234", listOf(a, b, c, d))

        val point = Coordinate(4f, 3f)

        assertTrue(p.containsCoordinate(point))
    }

    @Test
    fun `Complex Polygon test with point outside`() {
        val a = Coordinate(-0.5467512332629f, -0.7369002315514f)
        val b = Coordinate(0.3391845363938f, 1.61485653881f)
        val c = Coordinate(2.0466243833686f, 0.6644890768147f)
        val d = Coordinate(3.7379563072586f, 2.8229507701601f)
        val e = Coordinate(6.1380368468741f, -0.6080368468741f)
        val f = Coordinate(5.1071297694554f, -3.2980600020135f)
        val g = Coordinate(3.576877076412f, -3.1853045404208f)
        val h = Coordinate(2.3365669988926f, -1.1234903855834f)
        val i = Coordinate(0.9029618443572f, -3.3302758481828f)
        val j = Coordinate(-0.5306433101782f, -3.2014124635055f)
        val p = Polygon("1234", listOf(a, b, c, d, e, f, g, h, i, j))

        val point = Coordinate(2.3043511527232f, -1.8322390013088f)

        assertFalse(p.containsCoordinate(point))
    }

    @Test
    fun `Complex Polygon test with point inside`() {
        val a = Coordinate(-0.5467512332629f, -0.7369002315514f)
        val b = Coordinate(0.3391845363938f, 1.61485653881f)
        val c = Coordinate(2.0466243833686f, 0.6644890768147f)
        val d = Coordinate(3.7379563072586f, 2.8229507701601f)
        val e = Coordinate(6.1380368468741f, -0.6080368468741f)
        val f = Coordinate(5.1071297694554f, -3.2980600020135f)
        val g = Coordinate(3.576877076412f, -3.1853045404208f)
        val h = Coordinate(2.3365669988926f, -1.1234903855834f)
        val i = Coordinate(0.9029618443572f, -3.3302758481828f)
        val j = Coordinate(-0.5306433101782f, -3.2014124635055f)
        val p = Polygon("1234", listOf(a, b, c, d, e, f, g, h, i, j))

        val point = Coordinate(4.8010792308467f, -2.4765559246955f)

        assertTrue(p.containsCoordinate(point))
    }
}
