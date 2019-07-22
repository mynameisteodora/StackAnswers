package com.example.stackanswers.question

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackanswers.network.Question

class QuestionViewModel(question: Question, app: Application) : AndroidViewModel(app) {

    private val _selectedQuestion = MutableLiveData<Question>()

    val selectedQuestion : LiveData<Question>
        get() = _selectedQuestion

    init {
        _selectedQuestion.value = question
    }

}