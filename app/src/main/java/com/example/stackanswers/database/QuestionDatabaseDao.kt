package com.example.stackanswers.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.stackanswers.network.Question

@Dao
interface QuestionDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(questionBookmark: QuestionBookmark)

    @Update
    fun update(questionBookmark: QuestionBookmark)

    @Query("SELECT * from question_bookmark_table WHERE questionId = :key")
    fun get(key: Int): QuestionBookmark?

    @Query("DELETE FROM question_bookmark_table")
    fun clear()

    @Query("SELECT * FROM question_bookmark_table ORDER BY date_saved DESC")
    fun getAllQuestions(): LiveData<List<QuestionBookmark>>

    @Query("SELECT * FROM question_bookmark_table ORDER BY date_saved DESC LIMIT 1")
    fun getLastSavedQuestion(): QuestionBookmark?
}