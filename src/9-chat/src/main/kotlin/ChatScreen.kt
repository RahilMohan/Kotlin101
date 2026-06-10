import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(chatService: ChatService) {
    val messages by chatService.messages.collectAsState()
    val isTyping by chatService.isTyping.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Vibe Chat") })
        },
        bottomBar = {
            Surface(tonalElevation = 4.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Type a message...") }
                    )
                    Button(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                chatService.sendMessage(inputText)
                                inputText = ""
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f).padding(8.dp)) {
                items(messages) { msg ->
                    ChatBubble(msg)
                }
                if (isTyping) {
                    item {
                        Text(
                            "VibeBot is typing...",
                            modifier = Modifier.padding(8.dp),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(msg: ChatMessage) {
    val alignment = if (msg.isMe) Alignment.CenterEnd else Alignment.CenterStart
    val color =
        if (msg.isMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    val shape =
        if (msg.isMe) RoundedCornerShape(12.dp, 12.dp, 0.dp, 12.dp) else RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp)

    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), contentAlignment = alignment) {
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = color)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                if (!msg.isMe) {
                    Text(msg.sender, fontSize = 10.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
                Text(msg.content)
            }
        }
    }
}
