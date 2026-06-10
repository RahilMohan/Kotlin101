import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var cityInput by remember { mutableStateOf("London") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Weather Explorer", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = cityInput,
                onValueChange = { cityInput = it },
                label = { Text("Enter City") }
            )
            Button(
                onClick = { scope.launch { viewModel.fetchWeather(cityInput) } },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (val s = state) {
            is WeatherState.Loading -> {
                CircularProgressIndicator()
                Text("Fetching weather data...", modifier = Modifier.padding(top = 16.dp))
            }

            is WeatherState.Success -> {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(s.data.city, fontSize = 32.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        Text("${s.data.temperature}°C", fontSize = 48.sp)
                        Text(s.data.condition, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }

            is WeatherState.Error -> {
                Text("Error: ${s.message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
