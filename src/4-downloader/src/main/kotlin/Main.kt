import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.rendering.TextColors.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

fun main() = runBlocking {
    val t = Terminal()
    val downloader = Downloader()

    t.println(brightBlue("--- Async Multi-File Downloader ---"))

    val files = listOf(
        "kotlin-sdk.zip" to 100,
        "android-studio.dmg" to 500,
        "wear-os-image.iso" to 300,
        "gradle-dist.zip" to 150
    )

    t.println("Downloading ${files.size} files in parallel...")

    coroutineScope {
        files.forEach { (name, size) ->
            launch {
                downloader.downloadFile(name, size, (5..15).random()).collect { p ->
                    // Simplified output for now to ensure project completion
                    if (p % 20 == 0) {
                        t.println(gray("$name: $p%"))
                    }
                }
                t.println(green("$name: 100% [Complete]"))
            }
        }
    }

    t.println(brightBlue("\nAll downloads complete!"))
}
