import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

class Downloader {

    /**
     * Simulates downloading a file, emitting progress (0-100)
     */
    fun downloadFile(fileName: String, sizeMb: Int, speedMbPerSec: Int, failureProbability: Double = 0.0): Flow<Int> = flow {
        var downloaded = 0
        while (downloaded < sizeMb) {
            delay(100L) // Simulate 0.1 second of time passing
            if (Math.random() < failureProbability) {
                throw RuntimeException("Network failure while downloading $fileName")
            }
            downloaded += speedMbPerSec
            if (downloaded > sizeMb) downloaded = sizeMb
            val progress = (downloaded.toDouble() / sizeMb * 100).toInt()
            emit(progress)
        }
    }

    /**
     * Downloads multiple files in parallel
     */
    suspend fun downloadMultipleFiles(files: List<Pair<String, Int>>) = coroutineScope {
        files.map { (name, size) ->
            async {
                println("Starting download: $name")
                downloadFile(name, size, (1..5).random(), failureProbability = 0.1)
                    .catch { e ->
                        println("Failed: ${e.message}")
                    }
                    .collect { progress ->
                        // In a real CLI this would update a progress bar
                    }
                println("Finished download process: $name")
            }
        }.awaitAll()
    }
}
