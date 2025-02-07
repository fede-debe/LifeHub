package com.example.lifehub.ui.screens.notes.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehub.data.repositories.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {
    val uiState: MutableStateFlow<NoteCreationUiState> = MutableStateFlow(NoteCreationUiState(AddNote()))

    fun setTitle(title: String) {
        uiState.value = uiState.value.copy(note = uiState.value.note.copy(title = title))
    }

    fun setContent(content: String) {
        uiState.value = uiState.value.copy(note = uiState.value.note.copy(content = content))
    }

    fun createNote() {
        val currentNote = uiState.value.note
        if (currentNote.title.isNotBlank() && currentNote.content.isNotBlank()) {
            viewModelScope.launch {
                repository.createNote(
                    title = currentNote.title,
                    content = currentNote.content
                )
            }
        }
    }
}

data class NoteCreationUiState(
    val note: AddNote = AddNote()
) {
    val isNoteFilled: Boolean
        get() = note.content.isNotEmpty() && note.title.isNotEmpty()
}

data class AddNote(
    val title: String = "",
    val content: String = ""
)