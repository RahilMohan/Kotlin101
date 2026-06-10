import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val renderer = WatchFaceRenderer()

    Window(onCloseRequest = ::exitApplication, title = "Watch Face Preview") {
        WatchFaceScreen(renderer)
    }
}
