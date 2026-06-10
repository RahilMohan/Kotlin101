data class ComplicationState(
    val title: String,
    val icon: String,
    val isActive: Boolean
)

class SmartHomeComplicationProvider {
    private var currentState = ComplicationState("Lights", "💡", false)

    fun getComplicationData(): ComplicationState = currentState

    fun toggleState(): ComplicationState {
        currentState = currentState.copy(isActive = !currentState.isActive)
        return currentState
    }

    /**
     * Simulates the system request for a data update.
     */
    fun onUpdateRequested(): String {
        return "Complication Type: SHORT_TEXT, Value: ${if (currentState.isActive) "ON" else "OFF"}"
    }
}
