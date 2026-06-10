import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class TileLayoutFactoryTest {
    private val factory = TileLayoutFactory()

    @Test
    fun testLayoutContent() {
        val metrics = TileMetrics("Steps", "5,000", "Daily Goal", "#00FF00")
        val layout = factory.createWorkoutTile(metrics)

        assertTrue(layout.contains("Steps"))
        assertTrue(layout.contains("5,000"))
        assertTrue(layout.contains("#00FF00"))
    }

    @Test
    fun testLayoutContainsDescription() {
        val metrics = TileMetrics("Heart Rate", "72 bpm", "Resting", "#FF0000")
        val layout = factory.createWorkoutTile(metrics)

        assertTrue(layout.contains("Heart Rate"))
        assertTrue(layout.contains("72 bpm"))
        assertTrue(layout.contains("Resting"))
    }

    @Test
    fun testLayoutIsConsistent() {
        val metrics1 = TileMetrics("A", "1", "Desc", "#000")
        val metrics2 = TileMetrics("A", "1", "Desc", "#000")

        val layout1 = factory.createWorkoutTile(metrics1)
        val layout2 = factory.createWorkoutTile(metrics2)

        assertEquals(layout1, layout2, "Same input should produce same output")
    }

    @Test
    fun testLayoutContainsLayoutStructure() {
        val metrics = TileMetrics("Test", "Value", "Description", "#FFFFFF")
        val layout = factory.createWorkoutTile(metrics)

        assertTrue(layout.contains("Layout"))
        assertTrue(layout.contains("Column"))
        assertTrue(layout.contains("Text"))
    }

    @Test
    fun testEmptyMetrics() {
        val metrics = TileMetrics("", "", "", "")
        val layout = factory.createWorkoutTile(metrics)

        assertTrue(layout.contains("Layout"))
        // Should handle empty strings without crashing
    }

    @Test
    fun testSpecialCharactersInMetrics() {
        val metrics = TileMetrics("Step's Count", "5,000+", "Goal: 10k", "#FF00FF")
        val layout = factory.createWorkoutTile(metrics)

        assertTrue(layout.contains("Step's Count"))
        assertTrue(layout.contains("5,000+"))
        assertTrue(layout.contains("Goal: 10k"))
    }
}

private fun assertEquals(expected: String, actual: String, message: String? = null) {
    if (expected != actual) {
        throw AssertionError(message ?: "Expected '$expected' but was '$actual'")
    }
}
