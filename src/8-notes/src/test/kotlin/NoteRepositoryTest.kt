import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File
import org.junit.Test
import org.junit.Assert.*

class NoteRepositoryTest {

    @Test
    fun testAddAndDeleteNote() {
        runBlocking {
            val tempFile = File.createTempFile("notes", ".json")
            val repo = FileNoteRepository(tempFile)

            repo.addNote("Hello", "World")
            val notes = repo.getAllNotes().first()
            assertEquals(1, notes.size)
            assertEquals("Hello", notes[0].title)
            assertEquals("World", notes[0].content)

            repo.deleteNote(notes[0].id)
            assertTrue(repo.getAllNotes().first().isEmpty())

            tempFile.delete()
        }
    }

    @Test
    fun testAddMultipleNotes() {
        runBlocking {
            val tempFile = File.createTempFile("notes", ".json")
            val repo = FileNoteRepository(tempFile)

            repo.addNote("First", "Content 1")
            repo.addNote("Second", "Content 2")
            repo.addNote("Third", "Content 3")

            val notes = repo.getAllNotes().first()
            assertEquals(3, notes.size)
            assertEquals(listOf(1, 2, 3), notes.map { it.id })
            assertEquals(listOf("First", "Second", "Third"), notes.map { it.title })

            tempFile.delete()
        }
    }

    @Test
    fun testLoadFromExistingFile() {
        runBlocking {
            val tempFile = File.createTempFile("notes", ".json")

            // First session: save notes
            val repo1 = FileNoteRepository(tempFile)
            repo1.addNote("Persistent", "This should survive")
            val savedNotes = repo1.getAllNotes().first()
            assertEquals(1, savedNotes.size)

            // Second session: load notes from same file
            val repo2 = FileNoteRepository(tempFile)
            val loadedNotes = repo2.getAllNotes().first()
            assertEquals(1, loadedNotes.size)
            assertEquals("Persistent", loadedNotes[0].title)
            assertEquals("This should survive", loadedNotes[0].content)

            tempFile.delete()
        }
    }

    @Test
    fun testLoadFromInvalidJson() {
        runBlocking {
            val tempFile = File.createTempFile("notes", ".json")
            tempFile.writeText("{invalid json content}")

            // Should not crash, should return empty list
            val repo = FileNoteRepository(tempFile)
            val notes = repo.getAllNotes().first()
            assertTrue(notes.isEmpty())

            tempFile.delete()
        }
    }

    @Test
    fun testLoadFromNonexistentFile() {
        runBlocking {
            val nonexistentFile = File.createTempFile("nonexistent", ".json")
            nonexistentFile.delete() // Ensure it doesn't exist

            val repo = FileNoteRepository(nonexistentFile)
            val notes = repo.getAllNotes().first()
            assertTrue(notes.isEmpty())
        }
    }

    @Test
    fun testDeleteNotePreservesOthers() {
        runBlocking {
            val tempFile = File.createTempFile("notes", ".json")
            val repo = FileNoteRepository(tempFile)

            repo.addNote("Keep", "Keep me")
            repo.addNote("Delete", "Delete me")
            repo.addNote("AlsoKeep", "Keep me too")

            val notes = repo.getAllNotes().first()
            val toDelete = notes.find { it.title == "Delete" }
            assertNotNull(toDelete)

            repo.deleteNote(toDelete!!.id)
            val remaining = repo.getAllNotes().first()
            assertEquals(2, remaining.size)
            assertNull(remaining.find { it.title == "Delete" })
            assertNotNull(remaining.find { it.title == "Keep" })
            assertNotNull(remaining.find { it.title == "AlsoKeep" })

            tempFile.delete()
        }
    }

    @Test
    fun testFlowEmitsOnAdd() {
        runBlocking {
            val tempFile = File.createTempFile("notes", ".json")
            val repo = FileNoteRepository(tempFile)

            assertTrue(repo.getAllNotes().first().isEmpty())

            repo.addNote("New", "Note")
            assertEquals(1, repo.getAllNotes().first().size)

            tempFile.delete()
        }
    }
}
