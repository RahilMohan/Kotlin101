import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VibeScreen(viewModel: VibeViewModel) {
    val state by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vibe Count: ${state.count}",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = state.vibeLevel,
                fontSize = 18.sp,
                color = if (state.vibeLevel == "High Vibe") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = { viewModel.decrement() }) {
                    Text("-")
                }
                Button(onClick = { viewModel.increment() }) {
                    Text("+")
                }
            }
        }
    }
}
