package com.example.lifehub.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room Database that contains the Notes table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class LifeHubDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}