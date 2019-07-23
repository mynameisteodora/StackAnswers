package com.example.stackanswers.question

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackanswers.database.QuestionDatabaseDao
import com.example.stackanswers.network.Question
import javax.sql.DataSource

class QuestionViewModelFactory(
    private val question: Question,
    private val dataSource: QuestionDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(question, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
