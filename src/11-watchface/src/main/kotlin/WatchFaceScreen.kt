import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Composable
fun WatchFaceScreen(renderer: WatchFaceRenderer, isAmbient: Boolean = false) {
    var time by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) }

    LaunchedEffect(Unit) {
        while (true) {
            time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            kotlinx.coroutines.delay(1000)
        }
    }

    val angles = renderer.calculateAngles(time.hour, time.minute, time.second)

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2

            // Draw Face
            drawCircle(
                color = if (isAmbient) Color.Gray else Color.White,
                radius = radius,
                center = center,
                style = Stroke(width = 4f)
            )

            // Hour Hand
            val (hx, hy) = renderer.getHandOffset(angles.hour, radius * 0.5f)
            drawLine(
                color = Color.White,
                start = center,
                end = Offset(center.x + hx, center.y + hy),
                strokeWidth = 8f,
                cap = StrokeCap.Round
            )

            // Minute Hand
            val (mx, my) = renderer.getHandOffset(angles.minute, radius * 0.8f)
            drawLine(
                color = Color.White,
                start = center,
                end = Offset(center.x + mx, center.y + my),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )

            // Second Hand (Only in active mode)
            if (!isAmbient) {
                val (sx, sy) = renderer.getHandOffset(angles.second, radius * 0.9f)
                drawLine(
                    color = Color.Red,
                    start = center,
                    end = Offset(center.x + sx, center.y + sy),
                    strokeWidth = 2f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
