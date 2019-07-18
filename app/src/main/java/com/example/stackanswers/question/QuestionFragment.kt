package com.example.stackanswers.question

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.stackanswers.ANSWER_URL
import com.example.stackanswers.R
import com.example.stackanswers.databinding.FragmentQuestionBinding
import com.example.stackanswers.network.StackAnswerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class QuestionFragment : Fragment() {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private lateinit var binding : FragmentQuestionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        val questionTitle = QuestionFragmentArgs.fromBundle(arguments!!).questionTitle
        val questionBody = QuestionFragmentArgs.fromBundle(arguments!!).questionBody
        val topAnswerId = QuestionFragmentArgs.fromBundle(arguments!!).topAnswerId

        binding.questionTitle.text = questionTitle
        binding.questionBody.text = questionBody
        binding.answerTitle.text = "Top Answer Title"

        getTopAnswer(topAnswerId)

        return binding.root
    }

    private fun getTopAnswer(answerId: String) {
        coroutineScope.launch {
            // search for the top answer id for later display
            var getAnswersDeferred = StackAnswerApi.retrofitService.getAnswer(ANSWER_URL + answerId)

            try {
                val answerResponse = getAnswersDeferred.await()

                binding.answerBody.text = answerResponse.items[0].body
                Timber.i("Top answer response: ${answerResponse.items[0].body}")

            } catch (e: Exception) {
                // TODO function for pretty error handling
                Log.e("MainActivity", "This happened INCEPTION", e)
            }

        }

    }


}
