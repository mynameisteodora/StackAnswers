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
import timber.log.Timber

class SearchResultsFragment : Fragment() {


    private lateinit var binding : FragmentResultsSearchBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // initialise binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results_search, container, false)


        // set up the ViewModel
        val application = requireNotNull(this.activity).application
        val searchQuery = SearchResultsFragmentArgs.fromBundle(arguments!!).searchQuery
        Timber.i("The search query: $searchQuery")

        val viewModelFactory = SearchResultsViewModelFactory(searchQuery, application)

        val searchResultsViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchResultsViewModel::class.java)

        // connect the binding to the ViewModel
        binding.setLifecycleOwner(this)
        binding.searchResultsViewModel = searchResultsViewModel

        binding.searchBox.hint = searchQuery

        binding.questionList.adapter = SearchResultsAdapter()

//        searchResultsViewModel.navigateToQuestion.observe(this, Observer {
//            question ->
//            question?.let {
//                this.findNavController().navigate(
//                    SearchResultsFragmentDirections
//                        .actionSearchResultsFragmentToQuestionFragment(question.title, question.body, question.accepted_answer_id.toString())
//                )
//                searchResultsViewModel.doneNavigating()
//            }
//        })


        return binding.root
    }

}
