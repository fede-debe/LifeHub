package com.example.lifehub.data.repositories

import com.example.lifehub.data.db.dao.TodoDao
import com.example.lifehub.data.model.TodoItem
import com.example.lifehub.data.model.TodoList
import com.example.lifehub.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val dao: TodoDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend fun createTodoList(title: String, content: List<TodoItem>) {
        val todoListId = withContext(dispatcher) {
            UUID.randomUUID().toString()
        }

        val todoList = TodoList(
            id = todoListId,
            title = title,
            todoList = content
        )
         dao.upsert(todo = todoList)
    }

    fun getAllTodoList(): Flow<List<TodoList>> {
        return dao.getToDoLists()
    }

    fun getTodoList(id: String): Flow<TodoList> {
        return dao.getTodoListById(id = id)
    }

    suspend fun deleteTodoList(id: String) {
        dao.deleteById(id = id)
    }
}