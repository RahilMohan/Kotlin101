package domain

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double
)

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> = repository.getPopularMovies()
}

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getMovieById(id: Int): Movie?
}
