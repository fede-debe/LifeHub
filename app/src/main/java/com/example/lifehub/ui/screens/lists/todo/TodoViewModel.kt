package com.example.lifehub.ui.screens.lists.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehub.data.model.TodoList
import com.example.lifehub.data.repositories.TodoRepository
import com.example.lifehub.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoUiState(
    val items: List<TodoList> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    val uiState: StateFlow<TodoUiState> = repository.getAllTodoList().map { lists ->
        TodoUiState(
            items = lists,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = TodoUiState(isLoading = true)
    )

    fun deleteTodoList(id: String) = viewModelScope.launch {
        repository.deleteTodoList(id = id)
    }
}