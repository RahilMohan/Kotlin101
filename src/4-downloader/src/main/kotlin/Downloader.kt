import kotlinx.coroutines.*

class Downloader {

    suspend fun downloadFile(name: String, sizeMb: Int, onProgress: (Int) -> Unit): String {
        println("Starting download: $name ($sizeMb MB)")
        for (i in 1..10) {
            delay(100L) // Simulate network work
            onProgress(i * 10)
        }
        return "Finished $name"
    }

    suspend fun downloadMultiple(files: List<Pair<String, Int>>): List<String> = coroutineScope {
        files.map { (name, size) ->
            async {
                downloadFile(name, size) { progress ->
                    // Optional: Collect progress if needed
                }
            }
        }.awaitAll()
    }
}
