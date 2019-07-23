package com.example.stackanswers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDatabaseDao {
    @Insert
    fun insert(questionBookmark: QuestionBookmark)

    @Update
    fun update(questionBookmark: QuestionBookmark)

    @Query("SELECT * from question_bookmark_table WHERE questionId = :key")
    fun get(key: Int): QuestionBookmark?

    @Query("DELETE FROM question_bookmark_table")
    fun clear()

    @Query("SELECT * FROM question_bookmark_table ORDER BY date_saved DESC")
    fun getAllQuestions(): LiveData<List<QuestionBookmark>>
}