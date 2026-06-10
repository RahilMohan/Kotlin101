import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TilePreviewScreen(metrics: TileMetrics) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        // Wear OS Tile Frame (Circular)
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(metrics.title, color = Color.LightGray, fontSize = 14.sp)
                Text(
                    metrics.value,
                    color = Color(0xFFFFD700),
                    fontSize = 32.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )

                Text(metrics.description, color = Color.Gray, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.size(48.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                }
            }
        }
    }
}
