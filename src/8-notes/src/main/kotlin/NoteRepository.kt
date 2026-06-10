import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

@Serializable
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun addNote(title: String, content: String)
    suspend fun deleteNote(id: Int)
}

class FileNoteRepository(private val file: File) : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    private val json = Json { prettyPrint = true }

    init {
        loadNotes()
    }

    override fun getAllNotes(): Flow<List<Note>> = _notes.asStateFlow()

    override suspend fun addNote(title: String, content: String) {
        val currentNotes = _notes.value
        val nextId = (currentNotes.maxOfOrNull { it.id } ?: 0) + 1
        val newNote = Note(nextId, title, content)
        _notes.value = currentNotes + newNote
        saveNotes()
    }

    override suspend fun deleteNote(id: Int) {
        _notes.value = _notes.value.filter { it.id != id }
        saveNotes()
    }

    private fun loadNotes() {
        if (file.exists()) {
            try {
                val content = file.readText()
                _notes.value = json.decodeFromString(content)
            } catch (e: Exception) {
                _notes.value = emptyList()
            }
        }
    }

    private fun saveNotes() {
        file.writeText(json.encodeToString(_notes.value))
    }
}
