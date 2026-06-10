import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.math.PI

class WatchFaceRendererTest {
    private val renderer = WatchFaceRenderer()

    @Test
    fun testMidnight() {
        val angles = renderer.calculateAngles(0, 0, 0)
        assertEquals((-PI / 2).toFloat(), angles.hour, 0.001f)
        assertEquals((-PI / 2).toFloat(), angles.minute, 0.001f)
        assertEquals((-PI / 2).toFloat(), angles.second, 0.001f)
    }

    @Test
    fun testThreeOClock() {
        val angles = renderer.calculateAngles(3, 0, 0)
        assertEquals(0f, angles.hour, 0.001f)
    }

    @Test
    fun testSixOClock() {
        val angles = renderer.calculateAngles(6, 0, 0)
        assertEquals((PI / 2).toFloat(), angles.hour, 0.001f)
    }

    @Test
    fun testNineOClock() {
        val angles = renderer.calculateAngles(9, 0, 0)
        assertEquals(PI.toFloat(), angles.hour, 0.001f)
    }

    @Test
    fun testNoon() {
        val angles = renderer.calculateAngles(12, 0, 0)
        assertEquals((-PI / 2).toFloat(), angles.hour, 0.001f)
    }

    @Test
    fun testHalfPastThree() {
        val angles = renderer.calculateAngles(3, 30, 0)
        // Hour hand should be between 3 and 4
        assertTrue(angles.hour > 0f, "Hour hand should be past 3")
        assertTrue(angles.hour < (PI / 6).toFloat(), "Hour hand should be before 4")
    }

    @Test
    fun testSecondsHand() {
        val angles = renderer.calculateAngles(0, 0, 15)
        assertEquals(0f, angles.second, 0.001f) // 15s = 90° from top = 0 rad
    }

    @Test
    fun testMinutesHand() {
        val angles = renderer.calculateAngles(0, 15, 0)
        assertEquals(0f, angles.minute, 0.001f) // 15min = 90° from top = 0 rad
    }

    @Test
    fun testGetHandOffsetAtZero() {
        // At angle 0 (3 o'clock), x = length, y = 0
        val (x, y) = renderer.getHandOffset(0f, 10f)
        assertEquals(10f, x, 0.001f)
        assertEquals(0f, y, 0.001f)
    }

    @Test
    fun testGetHandOffsetAt90Degrees() {
        // At PI/2 (6 o'clock), x ≈ 0, y = length
        val (x, y) = renderer.getHandOffset((PI / 2).toFloat(), 10f)
        assertTrue(x < 0.001f, "x should be near 0 at 6 o'clock")
        assertEquals(10f, y, 0.001f)
    }

    @Test
    fun testGetHandOffsetAtTop() {
        // At -PI/2 (12 o'clock), x ≈ 0, y = -length
        val (x, y) = renderer.getHandOffset((-PI / 2).toFloat(), 10f)
        assertTrue(x < 0.001f, "x should be near 0 at 12 o'clock")
        assertEquals(-10f, y, 0.001f)
    }

    @Test
    fun testHourAdjustedByMinutes() {
        // At 1:30, hour hand should be between 1 and 2 (closer to 1.5)
        val anglesAt1 = renderer.calculateAngles(1, 0, 0)
        val anglesAt130 = renderer.calculateAngles(1, 30, 0)
        assertTrue(anglesAt130.hour > anglesAt1.hour, "1:30 hour hand should be past 1:00")
    }

    @Test
    fun testAllHandAnglesAreUnique() {
        val angles = renderer.calculateAngles(3, 15, 30)
        // All hands should be in different positions
        val epsilon = 0.01f
        assertTrue(
            kotlin.math.abs(angles.hour - angles.minute) > epsilon,
            "Hour and minute hands should be different"
        )
    }
}
