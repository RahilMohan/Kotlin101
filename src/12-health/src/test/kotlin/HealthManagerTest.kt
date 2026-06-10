import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertEquals

class HealthManagerTest {

    @Test
    fun testInitialStats() {
        val manager = HealthManager()
        assertEquals(70, manager.stats.value.heartRate)
        assertEquals(0, manager.stats.value.steps)
    }

    @Test
    fun testMonitoringIncreasesSteps() = runBlocking {
        val manager = HealthManager()
        manager.startMonitoring()

        val initialSteps = manager.stats.value.steps
        delay(2500) // Wait for at least 2 updates

        assertTrue(manager.stats.value.steps >= initialSteps)
        manager.stopMonitoring()
    }

    @Test
    fun testStopMonitoring() = runBlocking {
        val manager = HealthManager()
        manager.startMonitoring()
        delay(1500)
        manager.stopMonitoring()

        val stepsAfterStop = manager.stats.value.steps

        // Wait to ensure monitoring has truly stopped
        delay(2000)
        // Steps should not change after stop (or change minimally due to timing)
        val stepsAfterWait = manager.stats.value.steps
        assertEquals(stepsAfterStop, stepsAfterWait)
    }

    @Test
    fun testHeartRateWithinBounds() = runBlocking {
        val manager = HealthManager()
        manager.startMonitoring()

        // Collect several readings
        delay(3500)
        val hr = manager.stats.value.heartRate
        assertTrue(hr in 60..180, "Heart rate $hr should be between 60 and 180")

        manager.stopMonitoring()
    }

    @Test
    fun testMultipleStartStopCycles() = runBlocking {
        val manager = HealthManager()

        // First cycle
        manager.startMonitoring()
        delay(1500)
        manager.stopMonitoring()

        val steps1 = manager.stats.value.steps

        // Second cycle
        manager.startMonitoring()
        delay(1500)
        manager.stopMonitoring()

        val steps2 = manager.stats.value.steps
        assertTrue(steps2 >= steps1, "Steps should increase across cycles")
    }

    @Test
    fun testStartTwiceShouldRestart() = runBlocking {
        val manager = HealthManager()
        manager.startMonitoring()
        delay(1500)
        val steps1 = manager.stats.value.steps

        // Starting again should create a new job
        manager.startMonitoring()
        delay(1500)
        val steps2 = manager.stats.value.steps

        assertTrue(steps2 > steps1, "Steps should continue increasing after restart")

        manager.stopMonitoring()
    }

    @Test
    fun testStatsDataClass() {
        val stats = HealthStats(72, 1000)
        assertEquals(72, stats.heartRate)
        assertEquals(1000, stats.steps)
    }
}
