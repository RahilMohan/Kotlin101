import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ComplicationPreviewScreen(provider: SmartHomeComplicationProvider) {
    var state by remember { mutableStateOf(provider.getComplicationData()) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A1A)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Complication System Preview",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 32.dp)
)

            // Watch Face Background
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // Complication Slot
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(if (state.isActive) MaterialTheme.colorScheme.primaryContainer else Color.DarkGray)
                        .clickable {
                            state = provider.toggleState()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.icon, fontSize = 20.sp)
                        Text(
                            if (state.isActive) "ON" else "OFF",
                            fontSize = 10.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
}

                // Clock Hands (Mock)
                Divider(modifier = Modifier.width(80.dp).height(2.dp), color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Click the complication to toggle state", color = Color.Gray, fontSize = 12.sp)
            Text(
                "Provider Response: ${provider.onUpdateRequested()}",
                color = Color.Green,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
