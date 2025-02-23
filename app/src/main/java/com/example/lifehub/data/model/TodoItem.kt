package com.example.lifehub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoList(
    @PrimaryKey val id: String,
    val title: String,
    val todoList: List<TodoItem>,
)

data class TodoItem(
    val id: String,
    val content: String,
    var isDone: Boolean = false
)