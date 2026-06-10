import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class VibeViewModelTest {
    @Test
    fun testInitialState() {
        val vm = VibeViewModel()
        assertEquals(0, vm.uiState.value.count)
        assertEquals("Neutral", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testIncrement() {
        val vm = VibeViewModel()
        vm.increment()
        assertEquals(1, vm.uiState.value.count)
    }

    @Test
    fun testDecrement() {
        val vm = VibeViewModel()
        vm.decrement()
        assertEquals(-1, vm.uiState.value.count)
        assertEquals("Neutral", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testVibeLevels() {
        val vm = VibeViewModel()
        repeat(11) { vm.increment() }
        assertEquals("High Vibe", vm.uiState.value.vibeLevel)

        repeat(22) { vm.decrement() }
        assertEquals("Low Vibe", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testHighVibeThreshold() {
        val vm = VibeViewModel()
        repeat(10) { vm.increment() }
        assertEquals("Neutral", vm.uiState.value.vibeLevel)
        vm.increment() // 11th
        assertEquals("High Vibe", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testLowVibeThreshold() {
        val vm = VibeViewModel()
        repeat(10) { vm.decrement() }
        assertEquals("Neutral", vm.uiState.value.vibeLevel)
        vm.decrement() // -11
        assertEquals("Low Vibe", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testStateImmutability() {
        val vm = VibeViewModel()
        val state1 = vm.uiState.value
        vm.increment()
        val state2 = vm.uiState.value
        // Original state should not be mutated
        assertEquals(0, state1.count)
        assertEquals(1, state2.count)
    }

    @Test
    fun testMultipleIncrements() {
        val vm = VibeViewModel()
        repeat(5) { vm.increment() }
        assertEquals(5, vm.uiState.value.count)
    }

    @Test
    fun testMultipleDecrements() {
        val vm = VibeViewModel()
        repeat(7) { vm.decrement() }
        assertEquals(-7, vm.uiState.value.count)
    }

    @Test
    fun testCombinedIncrementDecrement() {
        val vm = VibeViewModel()
        vm.increment()
        vm.increment()
        vm.decrement()
        assertEquals(1, vm.uiState.value.count)
        assertEquals("Neutral", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testStateFlowEmitsUpdate() = runBlocking {
        val vm = VibeViewModel()
        vm.increment()
        val state = vm.uiState.first()
        assertEquals(1, state.count)
    }
}
