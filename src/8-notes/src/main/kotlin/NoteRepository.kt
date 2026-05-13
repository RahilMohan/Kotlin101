import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface NoteDataSource {
    fun getNotes(): Flow<List<Note>>
    suspend fun saveNote(note: Note)
    suspend fun deleteNote(id: String)
}

class InMemoryNoteDataSource : NoteDataSource {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    
    override fun getNotes(): Flow<List<Note>> = _notes.asStateFlow()

    override suspend fun saveNote(note: Note) {
        _notes.update { (it + note).distinctBy { n -> n.id } }
    }

    override suspend fun deleteNote(id: String) {
        _notes.update { it.filterNot { n -> n.id == id } }
    }
}

class NoteRepository(private val dataSource: NoteDataSource) {
    fun getAllNotes(): Flow<List<Note>> = dataSource.getNotes()
    suspend fun addNote(note: Note) = dataSource.saveNote(note)
    suspend fun removeNote(id: String) = dataSource.deleteNote(id)
}
