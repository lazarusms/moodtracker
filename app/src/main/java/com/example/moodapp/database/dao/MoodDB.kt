package com.example.moodapp.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moodapp.model.Mood

@Database(entities = [Mood::class], version = 1)
abstract class MoodDB : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    companion object {

        private lateinit var instance: MoodDB

        fun getDatabase(context: Context): MoodDB {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        MoodDB::class.java,
                        "mood_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}