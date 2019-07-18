package com.example.stackanswers.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.stackanswers.ANSWER_URL
import com.example.stackanswers.R
import com.example.stackanswers.databinding.FragmentStartBinding
import com.example.stackanswers.network.StackAnswerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class StartFragment: Fragment() {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    private lateinit var binding : FragmentStartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)

        binding.searchButton.setOnClickListener(
//            view -> onButtonClicked(view)
            Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_searchResultsFragment)
        )

        return binding.root
    }

    fun onButtonClicked(view: View) {

        Timber.i("Button clicked!")

        // Get the search string from the user
        var searchPhrase = binding.searchBox.text.toString()

        getStackExchangeQuestions(searchPhrase)

    }

    private fun getStackExchangeQuestions(searchPhrase: String) {

        coroutineScope.launch {
            var getQuestionsDeferred = StackAnswerApi.retrofitService.getQuestions(question = searchPhrase)

            try {
                var response = getQuestionsDeferred.await()
                if(response.items.size != 0) {

                    binding.questionTitle.text = response.items.get(0).title
                    binding.questionBody.text = response.items.get(0).body

                    binding.questionAnswer.text = response.items.get(0).accepted_answer_id.toString()
                    var topAnswerId = response.items.get(0).accepted_answer_id

                    var getAnswersDeferred = StackAnswerApi.retrofitService.getAnswer(ANSWER_URL + topAnswerId!!)

                    try {
                        var answerResponse = getAnswersDeferred.await()
                        binding.questionAnswer.text = answerResponse.items[0].body
                    } catch (e: Exception) {
                        Log.e("MainActivity", "This happened INCEPTION", e)
                    }
                }
                else {

                    binding.questionTitle.text = "Oops! We couldn't find anything :("
                    binding.questionBody.text = ""
                    binding.questionAnswer.text = ""
                }

            } catch (e: Exception) {
                Log.e("MainActivity", "This happened", e)
                binding.questionTitle.text = e.toString()
            }
        }

    }
}