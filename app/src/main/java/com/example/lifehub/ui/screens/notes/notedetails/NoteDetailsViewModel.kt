package com.example.lifehub.ui.screens.notes.notedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehub.data.model.Note
import com.example.lifehub.data.repositories.NotesRepository
import com.example.lifehub.navigation.DestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteDetailsUiState(
    val item: Note? = null,
    val isLoading: Boolean = false,
)

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val repository: NotesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: String = savedStateHandle[DestinationsArgs.NOTE_ID]!!

    val uiState: StateFlow<NoteDetailsUiState> =
        repository.getNoteDetails(noteId = noteId).map { note ->
            NoteDetailsUiState(item = note, isLoading = false)

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NoteDetailsUiState(isLoading = true)
        )

    fun deleteNote() = viewModelScope.launch {
        repository.deleteNote(noteId = noteId)
    }
}