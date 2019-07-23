package com.example.stackanswers.searchresults

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackanswers.network.Question
import com.example.stackanswers.network.StackAnswerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class SearchResultsViewModel(val searchQuery: String, application: Application) : AndroidViewModel(application) {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private lateinit var topAnswerId: String

    private val _navigateToSelectedQuestion = MutableLiveData<Question>()

    val navigateToSelectedQuestion : LiveData<Question>
        get() = _navigateToSelectedQuestion

    fun displayQuestion(question: Question) {
        _navigateToSelectedQuestion.value = question
    }

    fun displayQuestionComplete() {
        _navigateToSelectedQuestion.value = null
    }

    private val _questions = MutableLiveData<List<Question>>()

    val questions: LiveData<List<Question>>
        get() = _questions

    init {
        getStackExchangeQuestions(searchQuery)
    }

    private fun getStackExchangeQuestions(searchPhrase: String) {

        coroutineScope.launch {
            Timber.i("In coroutine")

            // search for matching questions
            var getQuestionsDeferred = StackAnswerApi.retrofitService.getQuestions(question = searchPhrase)

            try {

                var response = getQuestionsDeferred.await()

                // need to check that there is at least a questions matching with the search query
                if(response.items.isNotEmpty()) {
                    topAnswerId = response.items.get(0).accepted_answer_id.toString()

                    Timber.i("Type for response.items: ${response.items.javaClass.name}")

                    _questions.value = response.items as List<Question>

                    Timber.i("Top answer id: $topAnswerId")
                    //getTopAnswer(topAnswerId.toString())
                }
                else {
                    // TODO place this in an showError() function
                    // if the search was invalid
//                    binding.questionTitle.text = "Oops! We couldn't find anything :("
//                    binding.questionBody.text = ""
//                    binding.questionAnswer.text = ""
                }

            } catch (e: Exception) {
                // TODO make function for pretty error handling
                Log.e("MainActivity", "This happened", e)
//                binding.questionTitle.text = e.toString()
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}