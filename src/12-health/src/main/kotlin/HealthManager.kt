import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class HealthStats(
    val heartRate: Int,
    val steps: Int
)

class HealthManager {
    private val _stats = MutableStateFlow(HealthStats(70, 0))
    val stats: StateFlow<HealthStats> = _stats.asStateFlow()

    private var job: Job? = null

    fun startMonitoring() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                delay(1000)
                val current = _stats.value
                val newHr = (current.heartRate + (-2..2).random()).coerceIn(60, 180)
                val newSteps = current.steps + (1..10).random()
                _stats.value = HealthStats(newHr, newSteps)
            }
        }
    }

    fun stopMonitoring() {
        job?.cancel()
        job = null
    }
}
