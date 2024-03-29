package com.example.stackanswers.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stackanswers.network.Answer
import com.example.stackanswers.network.Question

@Entity(tableName = "question_bookmark_table")
data class QuestionBookmark (
    @PrimaryKey
    var questionId: Int,

    @ColumnInfo(name = "question_body")
    var questionBody: String,

    @ColumnInfo(name = "answer_id")
    var answerId: Int,

    @ColumnInfo(name = "answer_body")
    var answerBody: String,

    @ColumnInfo(name = "date_saved")
    var dateSaved: Long = System.currentTimeMillis()
)