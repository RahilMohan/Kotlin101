import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(repository: NoteRepository) {
    val notes by repository.getAllNotes().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    var showAddDialog by remember { mutableStateOf(false) }
    var titleInput by remember { mutableStateOf("") }
    var contentInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Notes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(notes) { note ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                note.title,
                                fontSize = 18.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(note.content, maxLines = 2)
                        }
                        IconButton(onClick = { scope.launch { repository.deleteNote(note.id) } }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add New Note") },
                text = {
                    Column {
                        TextField(value = titleInput, onValueChange = { titleInput = it }, label = { Text("Title") })
                        TextField(
                            value = contentInput,
                            onValueChange = { contentInput = it },
                            label = { Text("Content") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        scope.launch {
                            repository.addNote(titleInput, contentInput)
                            titleInput = ""
                            contentInput = ""
                            showAddDialog = false
                        }
                    }) {
                        Text("Add")
                    }
                }
            )
        }
    }
}
