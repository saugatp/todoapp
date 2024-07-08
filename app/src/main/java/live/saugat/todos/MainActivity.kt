package live.saugat.todos

import TodoView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import live.saugat.todos.domain.TodoRepository
import live.saugat.todos.domain.models.TodoDatabase
import live.saugat.todos.domain.models.TodoItem
import live.saugat.todos.ui.components.TodoScreen
import live.saugat.todos.ui.theme.TodosTheme
import live.saugat.todos.viewmodels.TodoViewmodel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = TodoDatabase.getDatabase(applicationContext)
        val repository = TodoRepository(database.todoDao())
        val factory = TodoViewmodel.provideFactory(repository)

        setContent {
            TodosTheme {
                val viewModel = remember {
                    ViewModelProvider(this, factory).get(TodoViewmodel::class.java)
                }
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TodoScreen(viewModel = viewModel)
                }
            }
        }
    }
}
