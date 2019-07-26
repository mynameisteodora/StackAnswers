package com.example.stackanswers.question

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackanswers.start.ANSWER_URL
import com.example.stackanswers.database.QuestionBookmark
import com.example.stackanswers.database.QuestionDatabaseDao
import com.example.stackanswers.network.Answer
import com.example.stackanswers.network.Question
import com.example.stackanswers.network.StackAnswerApi
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

class QuestionViewModel(question: Question, val dataSource: QuestionDatabaseDao, app: Application) : AndroidViewModel(app) {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val _selectedQuestion = MutableLiveData<Question>()

    val selectedQuestion : LiveData<Question>
        get() = _selectedQuestion

    private val _topAnswer = MutableLiveData<Answer>()

    val topAnswer : LiveData<Answer>
        get() = _topAnswer

    private var questionBookmark = MutableLiveData<QuestionBookmark?>()

    private val _bookmarkedQuestion = MutableLiveData<Boolean>()
    val bookmarkedQuestion : LiveData<Boolean>
        get() = _bookmarkedQuestion

    private val _questionInDatabase = MutableLiveData<Boolean>()
    val questionInDatabase : LiveData<Boolean>
        get() = _questionInDatabase

    init {
        _selectedQuestion.value = question
        checkQuestionInDatabase(question.question_id)
        getTopAnswer(question.accepted_answer_id.toString())
    }

    private fun getTopAnswer(answerId: String) {
        coroutineScope.launch {
            // search for the top answer id for later display
            var getAnswersDeferred = StackAnswerApi.retrofitService.getAnswer(ANSWER_URL + answerId)

            try {
                val answerResponse = getAnswersDeferred.await()

                _topAnswer.value = answerResponse.items[0]
                //binding.answerBody.text = answerResponse.items[0].body
                Timber.i("Top answer response: ${answerResponse.items[0].body}")

            } catch (e: Exception) {
                // TODO function for pretty error handling
                Log.e("MainActivity", "This happened INCEPTION", e)
            }

        }

    }

    private fun checkQuestionInDatabase(questionId : Int) {
        coroutineScope.launch {
            var response : Boolean = true
            withContext(Dispatchers.IO) {
                val result = dataSource.checkItemExists(questionId)

                response = result == 1
            }

            _questionInDatabase.value = response
        }

    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}