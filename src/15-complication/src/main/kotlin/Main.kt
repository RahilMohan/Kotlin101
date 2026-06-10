import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val provider = SmartHomeComplicationProvider()

    Window(onCloseRequest = ::exitApplication, title = "Smart Home Complication Preview") {
        ComplicationPreviewScreen(provider)
    }
}
