package com.example.stackanswers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuestionBookmark::class], version = 1, exportSchema = false)
abstract class QuestionBookmarkDatabase : RoomDatabase() {
    abstract val questionDatabaseDao : QuestionDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionBookmarkDatabase? = null

        fun getInstance(context: Context): QuestionBookmarkDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null ) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionBookmarkDatabase::class.java,
                        "question_bookmark_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}