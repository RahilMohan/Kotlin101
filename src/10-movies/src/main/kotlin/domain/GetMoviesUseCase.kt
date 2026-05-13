package domain

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getMovies()
            .sortedByDescending { it.rating }
    }
}
