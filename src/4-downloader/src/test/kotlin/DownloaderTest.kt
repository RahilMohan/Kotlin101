import kotlinx.coroutines.test.runTest
import kotlin.test.*
import kotlin.system.measureTimeMillis

class DownloaderTest {
    private val downloader = Downloader()

    @Test
    fun testSingleDownload() = runTest {
        val result = downloader.downloadFile("test.txt", 10) { progress ->
            assertTrue(progress >= 0 && progress <= 100)
        }
        assertEquals("Finished test.txt", result)
    }

    @Test
    fun testParallelExecution() = runTest {
        val files = listOf("file1" to 5, "file2" to 5, "file3" to 5)
        
        // Single download takes ~1000ms (10 * 100ms)
        // Parallel should take ~1000ms total, NOT 3000ms.
        val time = measureTimeMillis {
            val results = downloader.downloadMultiple(files)
            assertEquals(3, results.size)
        }
        
        println("Total time: ${time}ms")
        assertTrue(time < 2000, "Downloads should run in parallel")
    }
}
