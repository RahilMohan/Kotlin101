import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.channels.*

enum class LogLevel { INFO, WARN, ERROR }
data class LogEntry(val level: LogLevel, val message: String)
data class LogStats(val errorCount: Int, val totalProcessed: Int)

class LogParser {

    fun parseLogs(lines: List<String>): Flow<LogEntry> = flow {
        lines.forEach { line ->
            val level = when {
                line.contains("ERROR") -> LogLevel.ERROR
                line.contains("WARN") -> LogLevel.WARN
                else -> LogLevel.INFO
            }
            emit(LogEntry(level, line))
        }
    }.flowOn(Dispatchers.Default)

    suspend fun aggregateStats(logFlow: Flow<LogEntry>): LogStats = coroutineScope {
        val channel = Channel<LogLevel>()
        var errors = 0
        var total = 0

        // Producer
        launch {
            logFlow.collect { entry ->
                channel.send(entry.level)
            }
            channel.close()
        }

        // Consumer
        for (level in channel) {
            total++
            if (level == LogLevel.ERROR) errors++
        }

        LogStats(errors, total)
    }
}
