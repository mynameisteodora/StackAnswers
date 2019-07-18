package com.example.stackanswers.question


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.stackanswers.R
import com.example.stackanswers.databinding.FragmentQuestionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class QuestionFragment : Fragment() {

    private lateinit var binding : FragmentQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        val questionTitle = QuestionFragmentArgs.fromBundle(arguments!!).questionTitle
        val questionBody = QuestionFragmentArgs.fromBundle(arguments!!).questionBody
        val answerTitle = QuestionFragmentArgs.fromBundle(arguments!!).answerTitle
        val answerBody = QuestionFragmentArgs.fromBundle(arguments!!).answerBody

        binding.questionTitle.text = questionTitle
        binding.questionBody.text = questionBody
        binding.answerTitle.text = answerTitle
        binding.answerBody.text = answerBody

        return binding.root
    }


}
