import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val manager = SyncManager()

    Window(onCloseRequest = ::exitApplication, title = "Data Sync Preview") {
        SyncScreen(manager)
    }
}
