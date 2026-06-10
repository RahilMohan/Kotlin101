import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VibeState(
    val count: Int = 0,
    val vibeLevel: String = "Neutral"
)

class VibeViewModel {
    private val _uiState = MutableStateFlow(VibeState())
    val uiState: StateFlow<VibeState> = _uiState.asStateFlow()

    fun increment() {
        val newCount = _uiState.value.count + 1
        updateState(newCount)
    }

    fun decrement() {
        val newCount = _uiState.value.count - 1
        updateState(newCount)
    }

    private fun updateState(newCount: Int) {
        val level = when {
            newCount > 10 -> "High Vibe"
            newCount < -10 -> "Low Vibe"
            else -> "Neutral"
        }
        _uiState.value = _uiState.value.copy(count = newCount, vibeLevel = level)
    }
}
