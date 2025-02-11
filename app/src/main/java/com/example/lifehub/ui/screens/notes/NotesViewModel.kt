package com.example.lifehub.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehub.data.model.Note
import com.example.lifehub.data.repositories.NotesRepository
import com.example.lifehub.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class NotesUiState(
    val items: List<Note> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    repository: NotesRepository
) : ViewModel() {


    val uiState: StateFlow<NotesUiState> = repository.getNotesListFlow().map { notes ->
        NotesUiState(
            items = notes,
            isLoading = false,
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = NotesUiState(isLoading = true)
    )
}