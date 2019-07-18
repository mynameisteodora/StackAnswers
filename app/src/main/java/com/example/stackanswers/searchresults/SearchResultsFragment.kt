package com.example.stackanswers.searchresults


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.stackanswers.ANSWER_URL
import com.example.stackanswers.R
import com.example.stackanswers.databinding.FragmentResultsSearchBinding
import com.example.stackanswers.network.StackAnswerApi
import com.example.stackanswers.start.StartFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchResultsFragment : Fragment() {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private lateinit var binding : FragmentResultsSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results_search, container, false)
        val passedArg = SearchResultsFragmentArgs.fromBundle(arguments!!).searchQuery

        binding.searchBoxInfo.text = passedArg
        binding.searchBox.hint = passedArg

        getStackExchangeQuestions(passedArg)

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

                    binding.questionTitle.text = response.items.get(0).title
                    binding.questionBody.text = response.items.get(0).body

                    var topAnswerId = response.items.get(0).accepted_answer_id
                    binding.questionAnswer.text = topAnswerId.toString()

                    // search for the top answer id for later display
                    var getAnswersDeferred = StackAnswerApi.retrofitService.getAnswer(ANSWER_URL + topAnswerId!!)

                    try {
                        var answerResponse = getAnswersDeferred.await()
                        binding.questionAnswer.text = answerResponse.items[0].body
                    } catch (e: Exception) {
                        Log.e("MainActivity", "This happened INCEPTION", e)
                    }
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


}
