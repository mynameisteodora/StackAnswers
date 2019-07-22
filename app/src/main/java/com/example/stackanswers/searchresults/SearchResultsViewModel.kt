package com.example.stackanswers.searchresults

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.stackanswers.network.Question
import com.example.stackanswers.network.StackAnswerApi
import com.example.stackanswers.network.StackAnswerSearchExcerpt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import kotlin.math.min

class SearchResultsViewModel(query: String, application: Application) : AndroidViewModel(application) {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    // add a mutable live data for the view model
    private val _questionResultsList = MutableLiveData<ArrayList<Question>>()

    // external LiveData for the question list
    val questionResultsList : LiveData<ArrayList<Question>>
        get() = _questionResultsList

    private val _navigateToQuestion = MutableLiveData<Int>()
    val navigateToQuestion
        get() = _navigateToQuestion

    fun onQuestionClicked(id: Int) {
        _navigateToQuestion.value = id
    }

    fun onQuestionNavigated() {
        _navigateToQuestion.value = null
    }

    init {
        getStackExchangeQuestions(query)
    }

    private fun getStackExchangeQuestions(searchPhrase: String) {

        coroutineScope.launch {

            // search for matching questions
            var getQuestionsDeferred = StackAnswerApi.retrofitService.getQuestions(question = searchPhrase)

            try {

                var response = getQuestionsDeferred.await()

                // need to check that there is at least a question matching with the search query
                if(response.items.isNotEmpty()) {

                    Timber.i("Number of answers: ${response.items.size}")

//                    questionTitle = response.items[0].title
////                    binding.questionTitle.text = questionTitle
//                    Timber.i("Question title: ${questionTitle}")
//
//                    questionBody = response.items[0].body
////                    binding.questionBody.text = questionBody
//
                    var topAnswerId = response.items.get(0).accepted_answer_id.toString()
//                    binding.questionAnswer.text = topAnswerId
                    // TODO get the list of question titles
                    var numAnswers = min(response.items.size - 1, 9)
                    for(i in 0..numAnswers) {
                        Timber.i("Question $i: fetched")
                    }

                    var questionList = getQuestionsList(numAnswers, response)
                    _questionResultsList.value = questionList


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

    private fun getQuestionsList(numQuestions: Int, response: StackAnswerSearchExcerpt) : ArrayList<Question> {
        var questionList: ArrayList<Question> = arrayListOf()
        for(i in 0..numQuestions) {
            questionList.add(response.items[i])
        }

        return questionList
    }



}