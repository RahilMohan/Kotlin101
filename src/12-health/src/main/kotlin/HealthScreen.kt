import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HealthScreen(manager: HealthManager) {
    val stats by manager.stats.collectAsState()

    // Heartbeat animation
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Health Tracker", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(48.dp))

        Icon(
            Icons.Default.Favorite,
            contentDescription = "Heart",
            tint = Color.Red,
            modifier = Modifier.size(64.dp * scale)
        )

        Text("${stats.heartRate} BPM", fontSize = 32.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Total Steps", fontSize = 14.sp)
                Text(
                    "${stats.steps}",
                    fontSize = 28.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold
                )
            }
        }
    }
}
