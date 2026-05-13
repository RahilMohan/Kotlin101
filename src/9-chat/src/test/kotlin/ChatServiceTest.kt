import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class ChatServiceTest {
    private val service = ChatService()

    @Test
    fun testSerialization() {
        val msg = ChatMessage("Alice", "Hi!")
        val json = service.serializeMessage(msg)
        assertTrue(json.contains("\"sender\":\"Alice\""))
        
        val decoded = service.deserializeMessage(json)
        assertEquals(msg, decoded)
    }

    @Test
    fun testMessageFlow() = runTest {
        val messages = service.messageFlow.take(3).toList()
        assertEquals(3, messages.size)
        assertEquals("Bot", messages[0].sender)
        assertEquals("System", messages[1].sender)
    }
}
