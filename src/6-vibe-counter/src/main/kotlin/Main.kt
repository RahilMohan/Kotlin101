import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val viewModel = VibeViewModel()

    Window(onCloseRequest = ::exitApplication, title = "Vibe Counter") {
        VibeScreen(viewModel)
    }
}
