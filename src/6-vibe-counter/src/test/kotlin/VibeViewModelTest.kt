import kotlin.test.*

class VibeViewModelTest {
    @Test
    fun testIncrement() {
        val vm = VibeViewModel()
        vm.onIncrement()
        assertEquals(1, vm.uiState.value.count)
        assertEquals("Vibing", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testVibeTransition() {
        val vm = VibeViewModel()
        repeat(11) { vm.onIncrement() }
        assertEquals(11, vm.uiState.value.count)
        assertEquals("High", vm.uiState.value.vibeLevel)
    }

    @Test
    fun testReset() {
        val vm = VibeViewModel()
        vm.onIncrement()
        vm.onReset()
        assertEquals(0, vm.uiState.value.count)
        assertEquals("Neutral", vm.uiState.value.vibeLevel)
    }
}
