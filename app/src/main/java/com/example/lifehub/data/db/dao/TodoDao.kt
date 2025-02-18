package com.example.lifehub.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.lifehub.data.model.TodoList
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getToDoLists(): Flow<List<TodoList>>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodoListById(id: String): Flow<TodoList>

    @Upsert
    suspend fun upsert(todo: TodoList)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteById(id: String): Int

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoListByIdSync(id: String): TodoList?

    @Transaction
    suspend fun updateTodoItemIsDone(listId: String, itemId: String, isDone: Boolean) {
        // Retrieve the TodoList synchronously
        val todoList = getTodoListByIdSync(listId)
        if (todoList != null) {
            // Create a new list of TodoItems with the updated isDone flag for the matching item
            val updatedItems = todoList.todoList.map { item ->
                if (item.id == itemId) item.copy(isDone = isDone) else item
            }
            // Save the updated TodoList back to the database
            upsert(todoList.copy(todoList = updatedItems))
        }
    }
}