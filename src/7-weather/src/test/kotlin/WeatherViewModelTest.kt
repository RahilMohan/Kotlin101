import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlin.test.*

class WeatherViewModelTest {
    @Test
    fun testInitialLoadingState() {
        val vm = WeatherViewModel()
        assertEquals(WeatherState.Loading, vm.uiState.value)
    }

    @Test
    fun testSuccessfulFetch() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        val vm = WeatherViewModel(testDispatcher)
        
        vm.fetchWeather()
        assertEquals(WeatherState.Loading, vm.uiState.value)
        
        advanceUntilIdle()
        
        val state = vm.uiState.value
        assertTrue(state is WeatherState.Success)
        assertEquals("Kotlin City", state.city)
    }
}
