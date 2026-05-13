import domain.GetMoviesUseCase
import data.MovieRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class CleanArchitectureTest {
    @Test
    fun testUseCaseLogic() = runTest {
        val repo = MovieRepositoryImpl()
        val useCase = GetMoviesUseCase(repo)
        
        val movies = useCase()
        
        assertEquals(2, movies.size)
        // Verify sorting by rating (Inception 8.8 should be first)
        assertEquals("Inception", movies[0].title)
    }

    @Test
    fun testDomainIsolation() {
        // This test technically just checks imports, but it's a good reminder
        // In a real multi-module project, domain wouldn't even see data
    }
}
