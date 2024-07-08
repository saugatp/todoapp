package live.saugat.todos.domain

import kotlinx.coroutines.flow.Flow
import live.saugat.todos.domain.models.TodoDao
import live.saugat.todos.domain.models.TodoItem

class TodoRepository(private val todoDao: TodoDao) {
    val allTodos: Flow<List<TodoItem>> = todoDao.getAllTodos()
    suspend fun insertTodo(todo: TodoItem){
        todoDao.addTodo(todo)
    }
    suspend fun updateTodo(todo: TodoItem){
        todoDao.updateTodo(todo)
    }
    suspend fun deleteTodo(todo: TodoItem){
        todoDao.deleteTodo(todo)
    }
}