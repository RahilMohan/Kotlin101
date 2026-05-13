import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val city: String, val temp: Double) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherViewModel(private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default) {
    private val _uiState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchWeather() {
        _uiState.value = WeatherState.Loading
        
        // Use a scope provided by the caller or a simple GlobalScope for this simulation
        // In real Android, we'd use viewModelScope
        CoroutineScope(ioDispatcher).launch {
            try {
                delay(500L) // Simulate network delay
                _uiState.value = WeatherState.Success("Kotlin City", 22.5)
            } catch (e: Exception) {
                _uiState.value = WeatherState.Error("Failed to fetch weather")
            }
        }
    }
}
