package live.saugat.todos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import live.saugat.todos.domain.TodoRepository
import live.saugat.todos.domain.models.TodoItem

class TodoViewmodel(private val todoRepository: TodoRepository): ViewModel() {
    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()
    init {
        viewModelScope.launch {
            todoRepository.allTodos.collect{todolist ->
                _todos.value = todolist

            }
        }
    }
    fun addTodo(task:String){
        viewModelScope.launch {
            todoRepository.insertTodo(TodoItem(task = task))
        }
    }
    fun deleteTodo(todoItem: TodoItem){
        viewModelScope.launch {
            todoRepository.deleteTodo(todoItem)
        }
    }
    fun toggleCompletionTodo(todoItem: TodoItem){
        viewModelScope.launch {
            todoRepository.updateTodo(todoItem.copy(isCompleted = !todoItem.isCompleted))
        }
    }

    companion object{
        fun provideFactory(repository: TodoRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(TodoViewmodel::class.java)){
                    return TodoViewmodel(todoRepository = repository) as T
                }
                else
                    throw IllegalArgumentException("Unknown Viewmodel Class")
            }
        }
    }
}
