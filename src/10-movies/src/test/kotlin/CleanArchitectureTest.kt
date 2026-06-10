import data.MockMovieRepository
import domain.GetMoviesUseCase
import domain.Movie
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CleanArchitectureTest {

    @Test
    fun testGetMoviesUseCase() = runBlocking {
        val repo = MockMovieRepository()
        val useCase = GetMoviesUseCase(repo)

        val movies = useCase()
        assertEquals(3, movies.size)
        assertEquals("Inception", movies[0].title)
    }

    @Test
    fun testGetMovieByIdExisting() = runBlocking {
        val repo = MockMovieRepository()
        val movie = repo.getMovieById(1)
        assertNotNull(movie)
        assertEquals("Inception", movie.title)
        assertEquals(8.8, movie.rating)
    }

    @Test
    fun testGetMovieByIdNonexistent() = runBlocking {
        val repo = MockMovieRepository()
        val movie = repo.getMovieById(99)
        assertNull(movie)
    }

    @Test
    fun testAllMoviesHaveRequiredFields() = runBlocking {
        val repo = MockMovieRepository()
        val movies = repo.getPopularMovies()
        for (movie in movies) {
            assertTrue(movie.id > 0, "Movie id should be positive")
            assertTrue(movie.title.isNotBlank(), "Movie title should not be blank")
            assertTrue(movie.description.isNotBlank(), "Movie description should not be blank")
            assertTrue(movie.rating in 0.0..10.0, "Rating should be 0-10")
        }
    }

    @Test
    fun testMovieDataClassProperties() {
        val movie = Movie(1, "Test", "A test movie", 7.5)
        assertEquals(1, movie.id)
        assertEquals("Test", movie.title)
        assertEquals("A test movie", movie.description)
        assertEquals(7.5, movie.rating)
    }

    @Test
    fun testUseCaseDelegatesToRepository() = runBlocking {
        val repo = MockMovieRepository()
        val useCase = GetMoviesUseCase(repo)

        val fromUseCase = useCase()
        val fromRepo = repo.getPopularMovies()

        assertEquals(fromRepo.size, fromUseCase.size)
        for (i in fromRepo.indices) {
            assertEquals(fromRepo[i].id, fromUseCase[i].id)
            assertEquals(fromRepo[i].title, fromUseCase[i].title)
        }
    }

    @Test
    fun testMovieEquality() {
        val m1 = Movie(1, "Inception", "A dream within a dream", 8.8)
        val m2 = Movie(1, "Inception", "A dream within a dream", 8.8)
        assertEquals(m1, m2)
        assertEquals(m1.hashCode(), m2.hashCode())
    }
}
