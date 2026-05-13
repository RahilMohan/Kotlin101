import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class VibeState(
    val count: Int = 0,
    val vibeLevel: String = "Neutral"
)

class VibeViewModel {
    private val _uiState = MutableStateFlow(VibeState())
    val uiState = _uiState.asStateFlow()

    fun onIncrement() {
        _uiState.update { currentState ->
            val newCount = currentState.count + 1
            currentState.copy(
                count = newCount,
                vibeLevel = calculateVibe(newCount)
            )
        }
    }

    fun onDecrement() {
        _uiState.update { currentState ->
            val newCount = if (currentState.count > 0) currentState.count - 1 else 0
            currentState.copy(
                count = newCount,
                vibeLevel = calculateVibe(newCount)
            )
        }
    }

    fun onReset() {
        _uiState.value = VibeState()
    }

    private fun calculateVibe(count: Int): String = when {
        count > 20 -> "Epic"
        count > 10 -> "High"
        count > 0 -> "Vibing"
        else -> "Neutral"
    }
}
