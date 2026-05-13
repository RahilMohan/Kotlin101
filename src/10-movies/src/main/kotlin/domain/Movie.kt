package domain

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double
)

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}
