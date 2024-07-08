package live.saugat.todos.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val task:String,
    var isCompleted:Boolean = false
)
