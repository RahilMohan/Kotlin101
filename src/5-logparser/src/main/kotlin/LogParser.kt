import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class LogEntry(
    val timestamp: String,
    val level: String,
    val message: String
)

class LogParser {
    // Standard log format: [2026-05-26 10:00:00] ERROR: Something went wrong
    private val regex = Regex("""\[(.*)\]\s+(INFO|WARN|ERROR):\s+(.*)""")

    fun parseLine(line: String): LogEntry? {
        val match = regex.find(line) ?: return null
        val (timestamp, level, message) = match.destructured
        return LogEntry(timestamp, level, message)
    }

    fun parseLogs(lines: Flow<String>): Flow<LogEntry> =
        lines.mapNotNull { parseLine(it) }

    suspend fun aggregateStats(entries: Flow<LogEntry>): Map<String, Int> = coroutineScope {
        val stats = mutableMapOf<String, Int>()
        val channel = kotlinx.coroutines.channels.Channel<LogEntry>()

        val aggregatorJob = launch {
            for (entry in channel) {
                stats[entry.level] = stats.getOrDefault(entry.level, 0) + 1
            }
        }

        entries.collect { entry ->
            channel.send(entry)
        }
        channel.close()
        aggregatorJob.join()

        stats
    }
}
