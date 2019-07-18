package com.example.stackanswers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.stackanswers.network.StackAnswerApi
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

const val ANSWER_URL = "/answers/"

class MainActivity : AppCompatActivity() {

//    // Set up the coroutine
//    private var viewModelJob = Job()
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up Timber logging
        Timber.plant(Timber.DebugTree())
//
//        // Get a reference to the binding object and inflate the fragment views.
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


    }

//    fun onButtonClicked(view: View) {
//
//        Timber.i("Button clicked!")
//
//        // Get the search string from the user
//        var searchPhrase = binding.searchBox.text.toString()
//
//        getStackExchangeQuestions(searchPhrase)
//
//    }
//
//    private fun getStackExchangeQuestions(searchPhrase: String) {
//
//        coroutineScope.launch {
//            var getQuestionsDeferred = StackAnswerApi.retrofitService.getQuestions(question = searchPhrase)
//
//            try {
//                var response = getQuestionsDeferred.await()
//                if(response.items.size != 0) {
//
//                    binding.questionTitle.text = response.items.get(0).title
//                    binding.questionBody.text = response.items.get(0).body
//
//                    binding.questionAnswer.text = response.items.get(0).accepted_answer_id.toString()
//                    var topAnswerId = response.items.get(0).accepted_answer_id
//
//                    var getAnswersDeferred = StackAnswerApi.retrofitService.getAnswer(ANSWER_URL + topAnswerId!!)
//
//                    try {
//                        var answerResponse = getAnswersDeferred.await()
//                        binding.questionAnswer.text = answerResponse.items[0].body
//                    } catch (e: Exception) {
//                        Log.e("MainActivity", "This happened INCEPTION", e)
//                    }
//                }
//                else {
//
//                    binding.questionTitle.text = "Oops! We couldn't find anything :("
//                    binding.questionBody.text = ""
//                    binding.questionAnswer.text = ""
//                }
//
//            } catch (e: Exception) {
//                Log.e("MainActivity", "This happened", e)
//                binding.questionTitle.text = e.toString()
//            }
//        }
//
//    }

// Need to implement this in the fragment
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

}
