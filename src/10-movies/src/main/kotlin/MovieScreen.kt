import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.Movie
import domain.MovieRepository
import data.MockMovieRepository

@Composable
fun MovieListScreen(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text(
                "Popular Movies",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        items(movies) { movie ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onMovieClick(movie.id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(movie.title, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("Rating: ${movie.rating}", color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

@Composable
fun MovieDetailScreen(movieId: Int, repository: MovieRepository, onBack: () -> Unit) {
    val movie = remember { mutableStateOf<Movie?>(null) }

    LaunchedEffect(movieId) {
        movie.value = repository.getMovieById(movieId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) { Text("Back") }

        Spacer(modifier = Modifier.height(24.dp))

        movie.value?.let { m ->
            Text(m.title, fontSize = 32.sp, color = MaterialTheme.colorScheme.primary)
            Text("Rating: ${m.rating}", fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(m.description)
        } ?: Text("Loading...")
    }
}
