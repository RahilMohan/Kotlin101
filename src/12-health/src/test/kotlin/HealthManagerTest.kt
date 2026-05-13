import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceTimeBy
import kotlin.test.*

class HealthManagerTest {
    @Test
    fun testTrackingLifecycle() = runTest {
        val manager = HealthManager(this)
        
        assertEquals(0, manager.state.value.bpm)
        assertFalse(manager.state.value.isRunning)
        
        manager.startTracking()
        assertTrue(manager.state.value.isRunning)
        
        advanceTimeBy(1100) // Wait for first emission
        assertTrue(manager.state.value.bpm in 60..160)
        
        manager.stopTracking()
        assertFalse(manager.state.value.isRunning)
        assertEquals(0, manager.state.value.bpm)
    }
}
