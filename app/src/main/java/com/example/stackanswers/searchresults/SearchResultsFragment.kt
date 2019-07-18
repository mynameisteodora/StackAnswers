package com.example.stackanswers.searchresults


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.stackanswers.R
import com.example.stackanswers.databinding.FragmentResultsSearchBinding
import com.example.stackanswers.network.StackAnswerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class SearchResultsFragment : Fragment() {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private lateinit var binding : FragmentResultsSearchBinding

    private lateinit var questionTitle : String
    private lateinit var questionBody : String
    private lateinit var topAnswerId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results_search, container, false)
        val passedArg = SearchResultsFragmentArgs.fromBundle(arguments!!).searchQuery

        binding.searchBox.hint = passedArg

        getStackExchangeQuestions(passedArg)

        binding.readButton.setOnClickListener {
            // TODO check if the search was valid
            this.findNavController()
                .navigate(SearchResultsFragmentDirections.actionSearchResultsFragmentToQuestionFragment(questionTitle, questionBody, topAnswerId))
        }
        return binding.root
    }

    private fun getStackExchangeQuestions(searchPhrase: String) {

        coroutineScope.launch {

            // search for matching questions
            var getQuestionsDeferred = StackAnswerApi.retrofitService.getQuestions(question = searchPhrase)

            try {

                var response = getQuestionsDeferred.await()

                // need to check that there is at least a question matching with the search query
                if(response.items.isNotEmpty()) {

                    questionTitle = response.items[0].title
                    binding.questionTitle.text = questionTitle
                    Timber.i("Question title: ${questionTitle}")

                    questionBody = response.items[0].body
                    binding.questionBody.text = questionBody

                    topAnswerId = response.items.get(0).accepted_answer_id.toString()
                    binding.questionAnswer.text = topAnswerId

                    Timber.i("Top answer id: $topAnswerId")
                    //getTopAnswer(topAnswerId.toString())
                }
                else {
                    // TODO place this in an showError() function
                    // if the search was invalid
                    binding.questionTitle.text = "Oops! We couldn't find anything :("
                    binding.questionBody.text = ""
                    binding.questionAnswer.text = ""
                }

            } catch (e: Exception) {
                // TODO make function for pretty error handling
                Log.e("MainActivity", "This happened", e)
                binding.questionTitle.text = e.toString()
            }
        }

    }


}
