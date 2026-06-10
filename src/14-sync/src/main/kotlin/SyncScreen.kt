import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Composable
fun SyncScreen(manager: SyncManager) {
    var isSyncing by remember { mutableStateOf(false) }
    var lastSyncTime by remember { mutableStateOf("Never") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Data Sync Manager", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(48.dp))

        Box(contentAlignment = Alignment.Center) {
            if (isSyncing) {
                CircularProgressIndicator(modifier = Modifier.size(80.dp))
                Icon(Icons.Default.Refresh, contentDescription = "Syncing", tint = MaterialTheme.colorScheme.primary)
            } else {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Synced",
                    tint = Color.Green,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Status: ${if (isSyncing) "Synchronizing..." else "Up to date"}", fontSize = 18.sp)
        Text("Last Sync: $lastSyncTime", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                isSyncing = true
                scope.launch {
                    delay(2000)
                    isSyncing = false
                    lastSyncTime = java.time.LocalTime.now().toString().substring(0, 8)
                    manager.createPacket("settings", "Updated Settings")
                }
            },
            enabled = !isSyncing
        ) {
            Text("Sync Now")
        }
    }
}
