import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatMessage(
    val sender: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isMe: Boolean = false
)

class ChatService {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    fun sendMessage(content: String) {
        val msg = ChatMessage("Me", content, isMe = true)
        _messages.value = _messages.value + msg

        // Simulate bot reply
        simulateBotReply()
    }

    private fun simulateBotReply() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            _isTyping.value = true
            delay(2000)
            _isTyping.value = false
            val botMsg = ChatMessage("VibeBot", "I received your message! How can I help further?")
            _messages.value = _messages.value + botMsg
        }
    }
}
