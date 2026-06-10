import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

fun main() = application {
    val notesFile = File("notes.json")
    val repository = FileNoteRepository(notesFile)

    Window(onCloseRequest = ::exitApplication, title = "Notes App") {
        NotesScreen(repository)
    }
}
