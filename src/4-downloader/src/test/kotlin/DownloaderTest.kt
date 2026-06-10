import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class DownloaderTest {
    private val downloader = Downloader()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSingleFileDownload() = runTest {
        val progressList = mutableListOf<Int>()
        downloader.downloadFile("test.bin", 5, 1).collect {
            progressList.add(it)
        }

        assertEquals(100, progressList.last())
        assertTrue(testScheduler.currentTime >= 500L)
        // Verify progress is monotonic
        for (i in 1 until progressList.size) {
            assertTrue(progressList[i] >= progressList[i - 1], "Progress should be monotonic")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSmallFileCompletesInOneStep() = runTest {
        val progressList = mutableListOf<Int>()
        downloader.downloadFile("small.bin", 1, 5).collect {
            progressList.add(it)
        }

        assertEquals(100, progressList.last())
        assertEquals(1, progressList.size) // Single emit since speed >= size
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testProgressNeverExceeds100() = runTest {
        downloader.downloadFile("test.bin", 3, 2).collect { progress ->
            assertTrue(progress in 0..100, "Progress $progress should be between 0 and 100")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testZeroSizedFile() = runTest {
        val progressList = mutableListOf<Int>()
        downloader.downloadFile("empty.bin", 0, 1).collect {
            progressList.add(it)
        }
        // Size 0 means the while loop never executes, so no emits
        assertTrue(progressList.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testParallelDownloads() = runTest {
        // Verify multiple concurrent downloads don't interfere
        val results1 = mutableListOf<Int>()
        val results2 = mutableListOf<Int>()

        val job1 = launch {
            downloader.downloadFile("file1.bin", 3, 1).collect { results1.add(it) }
        }
        val job2 = launch {
            downloader.downloadFile("file2.bin", 2, 2).collect { results2.add(it) }
        }

        job1.join()
        job2.join()

        assertEquals(100, results1.last())
        assertEquals(100, results2.last())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDownloadFailure() = runTest {
        var errorCaught = false
        try {
            // Guaranteed failure (probability 1.0)
            downloader.downloadFile("fail.bin", 10, 1, failureProbability = 1.0).collect {
                // should not complete
            }
        } catch (e: RuntimeException) {
            errorCaught = true
            assertTrue(e.message!!.contains("Network failure"))
        }
        assertTrue(errorCaught, "Should have thrown a RuntimeException")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDownloadMultipleFiles() = runTest {
        val files = listOf("a.bin" to 2, "b.bin" to 3, "c.bin" to 1)
        downloader.downloadMultipleFiles(files)
        // Should complete without throwing
        assertTrue(true)
    }
}
