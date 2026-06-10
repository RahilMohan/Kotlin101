import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.LaunchedEffect

fun main() = application {
    val viewModel = WeatherViewModel()

    Window(onCloseRequest = ::exitApplication, title = "Weather App") {
        // Initial fetch inside the window (composable context)
        LaunchedEffect(Unit) {
            viewModel.fetchWeather("London")
        }
        WeatherScreen(viewModel)
    }
}
