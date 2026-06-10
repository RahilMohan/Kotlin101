import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.table.table
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File

fun main(args: Array<String>) = runBlocking {
    val t = Terminal()
    val parser = LogParser()

    t.println(brightBlue("--- Kotlin Log Analyzer ---"))

    val filePath = args.firstOrNull() ?: "sample.log"
    val file = File(filePath)

    if (!file.exists()) {
        t.println(yellow("Creating sample log file for demonstration: $filePath"))
        file.writeText(
            """
            [2026-05-26 10:00:01] INFO: System booting...
            [2026-05-26 10:00:05] INFO: Services started.
            [2026-05-26 10:01:12] WARN: Disk usage at 85%
            [2026-05-26 10:02:45] ERROR: Connection reset by peer
            [2026-05-26 10:03:00] INFO: User 'admin' logged in.
            [2026-05-26 10:04:10] ERROR: NullPointerException in AuthService
            [2026-05-26 10:05:22] INFO: Heartbeat signal received.
        """.trimIndent()
        )
    }

    t.println(gray("Analyzing log file: ${file.absolutePath}"))

    val lines = file.readLines().asFlow()
    val entries = parser.parseLogs(lines)
    val stats = parser.aggregateStats(entries)

    t.println(table {
        header { row(cyan("Level"), cyan("Count")) }
        body {
            stats.forEach { (level, count) ->
                val color = when (level) {
                    "ERROR" -> red
                    "WARN" -> yellow
                    else -> green
                }
                row(color(level), count.toString())
            }
        }
    })

    t.println(brightGreen("\nAnalysis Complete!"))
}
