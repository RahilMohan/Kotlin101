data class ComplicationDisplayData(
    val shortText: String,
    val iconId: String,
    val contentDescription: String
)

class ComplicationProvider {
    private var isLightOn: Boolean = false

    fun onUpdate(): ComplicationDisplayData {
        return if (isLightOn) {
            ComplicationDisplayData("ON", "ic_light_on", "Lights are active")
        } else {
            ComplicationDisplayData("OFF", "ic_light_off", "Lights are inactive")
        }
    }

    fun onUserTap(): Boolean {
        isLightOn = !isLightOn
        return isLightOn
    }
    
    fun getStatus(): Boolean = isLightOn
}
