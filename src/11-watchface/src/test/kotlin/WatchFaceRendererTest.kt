import kotlin.test.*

class WatchFaceRendererTest {
    private val renderer = WatchFaceRenderer(100f, 100f)

    @Test
    fun testSecondHandAngles() {
        assertEquals(0f, renderer.getSecondHandAngle(0))
        assertEquals(180f, renderer.getSecondHandAngle(30))
        assertEquals(354f, renderer.getSecondHandAngle(59))
    }

    @Test
    fun testHourHandAngles() {
        // 3 PM is 90 degrees
        assertEquals(90f, renderer.getHourHandAngle(15, 0))
        // 12 PM is 0 degrees
        assertEquals(0f, renderer.getHourHandAngle(12, 0))
    }

    @Test
    fun testAmbientModeHidesSeconds() {
        val output = renderer.render(12, 0, 30, true)
        assertTrue(output.contains("Second Hand Hidden"))
        assertFalse(output.contains("Second Hand Angle"))
    }
}
