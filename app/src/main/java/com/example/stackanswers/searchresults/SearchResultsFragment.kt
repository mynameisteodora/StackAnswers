package com.example.stackanswers.searchresults


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        var searchQueryText = "$searchQuery "

        binding.searchQuery.text = searchQueryText

        binding.questionList.adapter = SearchResultsAdapter(SearchResultsAdapter.OnClickListener {
            searchResultsViewModel.displayQuestion(it)
        })

        searchResultsViewModel.navigateToSelectedQuestion.observe(this, Observer {
            if(null != it) {
                this.findNavController().navigate(SearchResultsFragmentDirections.actionSearchResultsFragmentToQuestionFragment(it))
                searchResultsViewModel.displayQuestionComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

}
