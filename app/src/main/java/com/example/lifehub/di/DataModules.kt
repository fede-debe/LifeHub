package com.example.lifehub.di

import android.content.Context
import androidx.room.Room
import com.example.lifehub.data.db.LifeHubDatabase
import com.example.lifehub.data.db.dao.NotesDao
import com.example.lifehub.data.db.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): LifeHubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LifeHubDatabase::class.java,
            "LifeHub.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNotesDao(database: LifeHubDatabase): NotesDao = database.notesDao()

    @Provides
    fun provideTodoDao(database: LifeHubDatabase): TodoDao = database.todoDao()
}