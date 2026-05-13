import kotlin.test.*

class TileLayoutFactoryTest {
    private val factory = TileLayoutFactory()

    @Test
    fun testWorkoutTileLayout() {
        val metrics = WorkoutMetrics("12:45", 150, 0.65f)
        val layout = factory.buildWorkoutTile(metrics)
        
        assertTrue(layout is TileElement.Column)
        val column = layout as TileElement.Column
        
        assertEquals(4, column.children.size)
        assertTrue(column.children[0] is TileElement.Text)
        assertEquals("Workout Active", (column.children[0] as TileElement.Text).content)
        
        assertTrue(column.children[1] is TileElement.CircularProgress)
        assertEquals(0.65f, (column.children[1] as TileElement.CircularProgress).progress)
    }

    @Test
    fun testDslBuilder() {
        val layout = factory.tileLayout {
            add(TileElement.Text("Dynamic"))
            add(TileElement.CircularProgress(0.5f))
        }
        
        assertTrue(layout is TileElement.Column)
        assertEquals(2, (layout as TileElement.Column).children.size)
    }
}
