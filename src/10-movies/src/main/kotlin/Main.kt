import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import data.MockMovieRepository
import domain.GetMoviesUseCase
import domain.Movie

fun main() = application {
    val repository = MockMovieRepository()
    val getMoviesUseCase = GetMoviesUseCase(repository)
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }

    LaunchedEffect(Unit) {
        movies = getMoviesUseCase()
    }

    Window(onCloseRequest = ::exitApplication, title = "Movie Explorer") {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "list") {
            composable("list") {
                MovieListScreen(movies = movies, onMovieClick = { id ->
                    navController.navigate("detail/$id")
                })
            }
            composable("detail/{movieId}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
                MovieDetailScreen(movieId = movieId, repository = repository, onBack = {
                    navController.popBackStack()
                })
            }
        }
    }
}
