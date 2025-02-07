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

}