package com.example.lifehub.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifehub.R
import com.example.lifehub.data.model.Note
import com.example.lifehub.data.repositories.NotesRepository
import com.example.lifehub.navigation.DELETE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class NotesUiState(
    val items: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val snackBarMessage: Int? = null
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    repository: NotesRepository
) : ViewModel() {

    private val _snackBarMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    val uiState: StateFlow<NotesUiState> =
        combine(repository.getNotesListFlow(), _snackBarMessage) { notes, message ->
            NotesUiState(
                items = notes,
                isLoading = false,
                snackBarMessage = message
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NotesUiState(isLoading = true)
        )

    fun showResultMessage(result: Int) {
        when (result) {
            DELETE_RESULT_OK -> showSnackBarMessage(R.string.successfully_deleted_note_message)
        }
    }

    fun snackBarMessageShown() {
        _snackBarMessage.value = null
    }

    private fun showSnackBarMessage(message: Int) {
        _snackBarMessage.value = message
    }
}