package com.example.lifehub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lifehub.data.db.dao.NotesDao
import com.example.lifehub.data.db.dao.TodoDao
import com.example.lifehub.data.model.Note
import com.example.lifehub.data.model.TodoList

/**
 * The Room Database that contains the Notes table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Note::class, TodoList::class], version = 3, exportSchema = false)
@TypeConverters(KotlinConverters::class)
abstract class LifeHubDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun todoDao(): TodoDao
}