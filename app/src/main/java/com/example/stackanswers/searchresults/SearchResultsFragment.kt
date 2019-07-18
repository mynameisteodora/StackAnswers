package com.example.stackanswers.searchresults


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.stackanswers.ANSWER_URL
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
    private lateinit var answerTitle: String
    private lateinit var answerBody: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results_search, container, false)
        val passedArg = SearchResultsFragmentArgs.fromBundle(arguments!!).searchQuery

        //binding.searchBoxInfo.text = passedArg
        binding.searchBox.hint = passedArg

        getStackExchangeQuestions(passedArg)

        binding.readButton.setOnClickListener {
            this.findNavController()
                .navigate(SearchResultsFragmentDirections.actionSearchResultsFragmentToQuestionFragment(questionTitle, questionBody, answerTitle, answerBody))
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

                    var topAnswerId = response.items.get(0).accepted_answer_id
                    binding.questionAnswer.text = topAnswerId.toString()

                    Timber.i("Top answer id: $topAnswerId")
                    getTopAnswer(topAnswerId.toString())
                }
                else {
                    // if the search was invalid
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

    private fun getTopAnswer(answerId: String) {
        coroutineScope.launch {
            // search for the top answer id for later display
            var getAnswersDeferred = StackAnswerApi.retrofitService.getAnswer(ANSWER_URL + answerId)

            try {
                val answerResponse = getAnswersDeferred.await()

                answerTitle = "TOP ANSWER"
                answerBody = answerResponse.items[0].body
                binding.questionAnswer.text = answerResponse.items[0].body
                Timber.i("Top answer response: ${answerResponse.items[0].body}")
            } catch (e: Exception) {
                Log.e("MainActivity", "This happened INCEPTION", e)
            }

        }

    }


}
