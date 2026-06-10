import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.LaunchedEffect

fun main() = application {
    val manager = HealthManager()

    LaunchedEffect(Unit) {
        manager.startMonitoring()
    }

    Window(onCloseRequest = {
        manager.stopMonitoring()
        exitApplication()
    }, title = "Health Tracker Preview") {
        HealthScreen(manager)
    }
}
