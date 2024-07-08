package live.saugat.todos.ui.components

import TodoView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import live.saugat.todos.viewmodels.TodoViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewmodel) {
    val todos by viewModel.todos.collectAsState(initial = emptyList())
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
     Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Todos") }
                )
            },
         floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Add new task") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        showBottomSheet = true
                    }
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(todos, key = { it.id }) { todo ->
                    TodoView(
                        todo = todo,
                        onToggle = { viewModel.toggleCompletionTodo(todo) },
                        onDelete = { viewModel.deleteTodo(todo) }
                    )
                }
            }
         if(showBottomSheet) {
             ModalBottomSheet(
                 sheetState = bottomSheetState,
                 onDismissRequest = { showBottomSheet = false },
             ) {
                 AddTodoBottomSheet(onDismiss = { showBottomSheet = false }, onConfirm = { task ->
                     viewModel.addTodo(task)
                     showBottomSheet = false
                 })
             }
         }
        }
}