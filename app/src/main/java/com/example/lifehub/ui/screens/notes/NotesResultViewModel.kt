package com.example.lifehub.ui.screens.notes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NotesResultViewModel @Inject constructor() : ViewModel() {
    private val _snackBarMessage = MutableStateFlow<Int?>(null)
    val snackBarMessage: StateFlow<Int?> = _snackBarMessage

    fun postSnackBarMessage(message: Int) {
        _snackBarMessage.value = message
    }

    fun snackBarMessageShown() {
        _snackBarMessage.value = null
    }
}