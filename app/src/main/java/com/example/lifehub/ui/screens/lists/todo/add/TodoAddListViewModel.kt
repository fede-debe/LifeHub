package com.example.lifehub.ui.screens.lists.todo.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehub.data.model.TodoItem
import com.example.lifehub.data.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoCreationUiState(
    val title: String = "",
    val todoItems: List<TodoItem> = emptyList()
) {
    val isListValid: Boolean
        get() = todoItems.isNotEmpty()

    val isListReadyToSubmit: Boolean
        get() = title.isNotEmpty() && isListValid
}

@HiltViewModel
class TodoAddListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val uiState: MutableStateFlow<TodoCreationUiState> = MutableStateFlow(TodoCreationUiState())

    fun setTitle(title: String) {
        uiState.value = uiState.value.copy(title = title)
    }

    fun addTodoItem(item: TodoItem) {
            val updatedList = uiState.value.todoItems.plus(item)
            uiState.value = uiState.value.copy(todoItems = updatedList)
    }

    fun removeTodoItem(todoItem: TodoItem) {
        val updatedList = uiState.value.todoItems.filterNot { it.id == todoItem.id }
        uiState.value = uiState.value.copy(todoItems = updatedList)
    }

    fun createTodoList() {
        val currentList = uiState.value
        if (currentList.isListValid) {
            viewModelScope.launch {
                repository.createTodoList(
                    title = currentList.title,
                    content = currentList.todoItems
                )
            }
        }
    }
}


