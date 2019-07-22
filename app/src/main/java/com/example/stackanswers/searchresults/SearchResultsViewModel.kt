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

    private lateinit var questionTitle : String
    private lateinit var questionBody : String
    private lateinit var topAnswerId: String

    private val _navigateToQuestion = MutableLiveData<Question>()

    val navigateToQuestion : LiveData<Question>
        get() = _navigateToQuestion

    fun doneNavigating() {
        _navigateToQuestion.value = null
    }

    private val _question = MutableLiveData<Question>()

    val question: LiveData<Question>
        get() = _question

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

                // need to check that there is at least a question matching with the search query
                if(response.items.isNotEmpty()) {

//                    questionTitle = response.items[0].title
//                    binding.questionTitle.text = questionTitle
//                    Timber.i("Question title: ${questionTitle}")
//
//                    questionBody = response.items[0].body
//                    binding.questionBody.text = questionBody
//
                    topAnswerId = response.items.get(0).accepted_answer_id.toString()
//                    binding.questionAnswer.text = topAnswerId

                    _question.value = response.items[0]

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

}