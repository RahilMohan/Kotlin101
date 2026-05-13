import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class NoteRepositoryTest {
    @Test
    fun testAddAndListNotes() = runTest {
        val dataSource = InMemoryNoteDataSource()
        val repo = NoteRepository(dataSource)
        
        val newNote = Note("1", "Hello", "Kotlin World")
        repo.addNote(newNote)
        
        val notes = repo.getAllNotes().first()
        assertEquals(1, notes.size)
        assertEquals("Hello", notes[0].title)
    }

    @Test
    fun testDeleteNote() = runTest {
        val repo = NoteRepository(InMemoryNoteDataSource())
        repo.addNote(Note("1", "Title", "Content"))
        repo.removeNote("1")
        
        val notes = repo.getAllNotes().first()
        assertTrue(notes.isEmpty())
    }
}
