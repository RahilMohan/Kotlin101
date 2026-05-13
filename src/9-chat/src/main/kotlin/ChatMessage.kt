import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val sender: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
