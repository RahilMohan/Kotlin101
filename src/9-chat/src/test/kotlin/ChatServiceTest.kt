import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ChatServiceTest {

    @Test
    fun testSendMessage() = runBlocking {
        val service = ChatService()
        service.sendMessage("Hello")

        val messages = service.messages.value
        assertEquals(1, messages.size)
        assertEquals("Hello", messages[0].content)
        assertEquals("Me", messages[0].sender)
        assertTrue(messages[0].isMe)
    }

    @Test
    fun testBotReply() = runBlocking {
        val service = ChatService()
        service.sendMessage("Ping")

        // Wait for bot reply
        delay(3500)

        val messages = service.messages.value
        assertEquals(2, messages.size)
        assertEquals("VibeBot", messages[1].sender)
        assertFalse(messages[1].isMe)
    }

    @Test
    fun testMultipleMessages() = runBlocking {
        val service = ChatService()
        service.sendMessage("First")
        delay(3500) // Wait for bot reply
        service.sendMessage("Second")
        delay(3500) // Wait for bot reply

        val messages = service.messages.value
        assertEquals(4, messages.size) // msg1, bot1, msg2, bot2
        assertEquals("First", messages[0].content)
        assertEquals("Second", messages[2].content)
    }

    @Test
    fun testBotReplyContent() = runBlocking {
        val service = ChatService()
        service.sendMessage("Test")

        delay(3500)

        val messages = service.messages.value
        assertEquals(2, messages.size)
        assertTrue(messages[1].content.contains("I received your message"))
    }

    @Test
    fun testTypingStateTransitions() = runBlocking {
        val service = ChatService()
        assertFalse(service.isTyping.value)

        service.sendMessage("Hello")

        // Bot shouldn't be typing immediately
        delay(500)
        assertFalse(service.isTyping.value) // Still in 1s initial delay

        // After 1.5s, bot should be typing
        delay(1500)
        assertTrue(service.isTyping.value)

        // After 4s total, bot should finish
        delay(3000)
        assertFalse(service.isTyping.value)
    }

    @Test
    fun testEmptyMessage() = runBlocking {
        val service = ChatService()
        service.sendMessage("")
        assertEquals(1, service.messages.value.size)
        assertEquals("", service.messages.value[0].content)
    }

    @Test
    fun testMessageTimestamps() = runBlocking {
        val service = ChatService()
        service.sendMessage("First")
        val ts1 = service.messages.value.last().timestamp

        delay(100)
        service.sendMessage("Second")
        val ts2 = service.messages.value.last().timestamp
        assertTrue(ts2 > ts1, "Second message should have later timestamp")
    }
}
