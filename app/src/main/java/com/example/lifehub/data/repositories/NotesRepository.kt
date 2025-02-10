package com.example.lifehub.data.repositories

import com.example.lifehub.data.db.dao.NotesDao
import com.example.lifehub.data.model.Note
import com.example.lifehub.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val dao: NotesDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun createNote(title: String, content: String) {
        val noteId = withContext(dispatcher) {
            UUID.randomUUID().toString()
        }

        val note = Note(
            id = noteId,
            title = title,
            content = content
        )
        dao.upsert(note = note)
    }

    fun getNotesListFlow(): Flow<List<Note>> {
        return dao.getNotesList()
    }

    fun getNoteDetails(noteId: String): Flow<Note> {
        return dao.getNoteById(noteId = noteId)
    }

    suspend fun deleteNote(noteId: String) {
        dao.deleteById(noteId = noteId)
    }
}