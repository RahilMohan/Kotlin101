package data

import domain.Movie
import domain.MovieRepository

class MockMovieRepository : MovieRepository {
    private val movies = listOf(
        Movie(1, "Inception", "A thief who steals corporate secrets through the use of dream-sharing technology.", 8.8),
        Movie(
            2,
            "The Matrix",
            "A computer hacker learns from mysterious rebels about the true nature of his reality.",
            8.7
        ),
        Movie(
            3,
            "Interstellar",
            "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            8.6
        )
    )

    override suspend fun getPopularMovies(): List<Movie> = movies

    override suspend fun getMovieById(id: Int): Movie? = movies.find { it.id == id }
}
