package com.example.stackanswers.searchresults

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.stackanswers.R
import com.example.stackanswers.databinding.FragmentResultsSearchBinding

class SearchResultsFragment : Fragment() {

    private lateinit var binding : FragmentResultsSearchBinding

    private lateinit var questionTitle : String
    private lateinit var questionBody : String
    private lateinit var topAnswerId: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results_search, container, false)

        val searchPhrase = SearchResultsFragmentArgs.fromBundle(arguments!!).searchQuery
        val viewModelFactory = SearchResultsViewModelFactory(searchPhrase, application)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchResultsViewModel::class.java)

        binding.viewModel = viewModel


        binding.setLifecycleOwner(this)


        binding.searchBox.hint = searchPhrase


//        binding.readButton.setOnClickListener {
//            // TODO check if the search was valid
//            this.findNavController()
//                .navigate(SearchResultsFragmentDirections.actionSearchResultsFragmentToQuestionFragment(questionTitle, questionBody, topAnswerId))
//        }

        viewModel.navigateToQuestion.observe(this, Observer { questionId ->
            questionId?.let {
                this.findNavController().navigate(
                    SearchResultsFragmentDirections.actionSearchResultsFragmentToQuestionFragment(questionTitle, questionBody, topAnswerId)
                )
                viewModel.onQuestionNavigated()
            }
        })


        val questionAdapter = SearchResultsAdapter(SearchResultsAdapter.SearchResultsListener {
                questionId -> viewModel.onQuestionClicked(questionId)
        })
        binding.questionList.adapter = questionAdapter


        viewModel.questionResultsList.observe(this, Observer {
            it?.let {
                questionAdapter.submitList(it)
            }
        })

        return binding.root
    }



}
