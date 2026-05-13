package data

import domain.Movie
import domain.MovieRepository
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val movie_id: Int,
    val movie_title: String,
    val overview: String,
    val vote_average: Double
)

fun MovieDto.toDomain() = Movie(
    id = movie_id,
    title = movie_title,
    description = overview,
    rating = vote_average
)

class MovieRepositoryImpl : MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        // Simulate a network response
        val dtos = listOf(
            MovieDto(1, "Interstellar", "Space exploration...", 8.7),
            MovieDto(2, "Inception", "Dream heist...", 8.8)
        )
        return dtos.map { it.toDomain() }
    }
}
