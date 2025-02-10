package com.example.lifehub.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.lifehub.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    /**
     * Insert or update a note in the database. If a note already exists, replace it.
     *
     * @param note the note to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(note: Note)

    /**
     * Observes list of notes.
     *
     * @return all notes.
     */
    @Query("SELECT * FROM note")
    fun getNotesList(): Flow<List<Note>>

    /**
     * Observes a single note.
     *
     * @param noteId the note id.
     * @return the note with noteId.
     */
    @Query("SELECT * FROM note WHERE id = :noteId")
    fun getNoteById(noteId: String): Flow<Note>

    /**
     * Delete a note by id.
     *
     * @return the number of notes deleted. This should always be 1.
     */
    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteById(noteId: String): Int

}