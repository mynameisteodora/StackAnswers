package com.example.stackanswers.question

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackanswers.ANSWER_URL
import com.example.stackanswers.network.Answer
import com.example.stackanswers.network.Question
import com.example.stackanswers.network.StackAnswerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class QuestionViewModel(question: Question, app: Application) : AndroidViewModel(app) {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val _selectedQuestion = MutableLiveData<Question>()

    val selectedQuestion : LiveData<Question>
        get() = _selectedQuestion

    private val _topAnswer = MutableLiveData<Answer>()

    val topAnswer : LiveData<Answer>
        get() = _topAnswer

    init {
        _selectedQuestion.value = question
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

}