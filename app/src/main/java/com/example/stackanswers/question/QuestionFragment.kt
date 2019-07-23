package com.example.stackanswers.question

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentQuestionBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        val question = QuestionFragmentArgs.fromBundle(arguments!!).question
        val viewModelFactory = QuestionViewModelFactory(question, application)

        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(QuestionViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

}
