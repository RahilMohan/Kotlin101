import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class ChatService(private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default) {
    
    // Simulate incoming messages from a JSON WebSocket
    val messageFlow: Flow<ChatMessage> = channelFlow {
        val messages = listOf(
            ChatMessage("Bot", "Welcome to Kotlin Chat!"),
            ChatMessage("System", "You are now connected."),
            ChatMessage("Bot", "How is your learning journey?")
        )
        
        for (msg in messages) {
            send(msg)
            delay(100) // Simulate time between messages
        }
    }.flowOn(ioDispatcher)

    fun serializeMessage(message: ChatMessage): String {
        return Json.encodeToString(message)
    }

    fun deserializeMessage(json: String): ChatMessage {
        return Json.decodeFromString(json)
    }
}
