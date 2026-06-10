import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class WeatherViewModelTest {
    @Test
    fun testInitialStateIsLoading() {
        val vm = WeatherViewModel()
        assertTrue(vm.uiState.value is WeatherState.Loading)
    }

    @Test
    fun testWeatherFetchSuccessTokyo() = runBlocking {
        val vm = WeatherViewModel()
        vm.fetchWeather("Tokyo")
        val state = vm.uiState.value
        assertTrue(state is WeatherState.Success)
        if (state is WeatherState.Success) {
            assertEquals("Tokyo", state.data.city)
            assertEquals(22.0, state.data.temperature)
            assertEquals("Sunny", state.data.condition)
        }
    }

    @Test
    fun testWeatherFetchSuccessLondon() = runBlocking {
        val vm = WeatherViewModel()
        vm.fetchWeather("London")
        val state = vm.uiState.value
        assertTrue(state is WeatherState.Success)
        if (state is WeatherState.Success) {
            assertEquals("London", state.data.city)
            assertEquals(15.5, state.data.temperature)
            assertEquals("Cloudy", state.data.condition)
        }
    }

    @Test
    fun testWeatherFetchError() = runBlocking {
        val vm = WeatherViewModel()
        vm.fetchWeather("Mars")
        val state = vm.uiState.value
        assertTrue(state is WeatherState.Error)
        if (state is WeatherState.Error) {
            assertEquals("City not found", state.message)
        }
    }

    @Test
    fun testWeatherFetchErrorCaseInsensitive() = runBlocking {
        val vm = WeatherViewModel()
        vm.fetchWeather("london") // lowercase should still work
        val state = vm.uiState.value
        assertTrue(state is WeatherState.Success)
        if (state is WeatherState.Success) {
            assertEquals("London", state.data.city)
        }
    }

    @Test
    fun testLoadingStateTransitionsToResult() = runBlocking {
        val vm = WeatherViewModel()
        // Before fetch, state is Loading
        assertTrue(vm.uiState.value is WeatherState.Loading)

        vm.fetchWeather("Tokyo")
        // After fetch completes, state should be Success
        val state = vm.uiState.value
        assertTrue(state is WeatherState.Success, "Expected Success but got $state")
    }

    @Test
    fun testMultipleFetches() = runBlocking {
        val vm = WeatherViewModel()
        vm.fetchWeather("Tokyo")
        assertTrue(vm.uiState.value is WeatherState.Success)

        vm.fetchWeather("London")
        val state = vm.uiState.value
        assertTrue(state is WeatherState.Success)
        if (state is WeatherState.Success) {
            assertEquals("London", state.data.city)
        }
    }

    @Test
    fun testFetchThenError() = runBlocking {
        val vm = WeatherViewModel()
        vm.fetchWeather("Tokyo")
        assertTrue(vm.uiState.value is WeatherState.Success)

        vm.fetchWeather("UnknownCity")
        assertTrue(vm.uiState.value is WeatherState.Error)
    }
}
