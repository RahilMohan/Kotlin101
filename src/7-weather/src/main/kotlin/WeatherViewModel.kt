import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val city: String,
    val temperature: Double,
    val condition: String
)

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val data: WeatherData) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherViewModel {
    private val _uiState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val uiState: StateFlow<WeatherState> = _uiState.asStateFlow()

    suspend fun fetchWeather(city: String) {
        _uiState.value = WeatherState.Loading
        delay(1500) // Simulate network delay

        try {
            // Simulated response
            val data = when (city.lowercase()) {
                "london" -> WeatherData("London", 15.5, "Cloudy")
                "tokyo" -> WeatherData("Tokyo", 22.0, "Sunny")
                else -> throw Exception("City not found")
            }
            _uiState.value = WeatherState.Success(data)
        } catch (e: Exception) {
            _uiState.value = WeatherState.Error(e.message ?: "Unknown Error")
        }
    }
}
