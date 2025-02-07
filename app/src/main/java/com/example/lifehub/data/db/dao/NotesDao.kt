package com.example.lifehub.data.db.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.lifehub.data.model.Note

@Dao
interface NotesDao {
    /**
     * Insert or update a note in the database. If a note already exists, replace it.
     *
     * @param note the note to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(note: Note)

}