import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val metrics = TileMetrics("Workout", "00:42:15", "Active calories: 350", "#FFD700")

    Window(onCloseRequest = ::exitApplication, title = "Wear OS Tile Preview") {
        TilePreviewScreen(metrics)
    }
}
