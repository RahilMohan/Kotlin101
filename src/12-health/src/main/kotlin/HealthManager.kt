import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

data class HeartRateState(
    val bpm: Int = 0,
    val accuracy: String = "Unknown",
    val isRunning: Boolean = false
)

class HealthManager(private val scope: CoroutineScope) {
    private val _state = MutableStateFlow(HeartRateState())
    val state = _state.asStateFlow()

    private var sensorJob: Job? = null

    fun startTracking() {
        if (_state.value.isRunning) return
        
        _state.update { it.copy(isRunning = true, accuracy = "High") }
        
        sensorJob = scope.launch {
            while (isActive) {
                val nextBpm = Random.nextInt(60, 160)
                _state.update { it.copy(bpm = nextBpm) }
                delay(1000) // Emit every second
            }
        }
    }

    fun stopTracking() {
        sensorJob?.cancel()
        _state.update { it.copy(isRunning = false, bpm = 0, accuracy = "None") }
    }
}
