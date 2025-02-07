package com.example.lifehub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifehub.data.db.dao.NotesDao
import com.example.lifehub.data.model.Note

/**
 * The Room Database that contains the Notes table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class LifeHubDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}