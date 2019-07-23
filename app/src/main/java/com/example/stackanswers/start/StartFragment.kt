package com.example.stackanswers.start

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stackanswers.R
import com.example.stackanswers.database.QuestionBookmarkDatabase
import com.example.stackanswers.databinding.FragmentStartBinding


class StartFragment: Fragment() {

    private lateinit var binding : FragmentStartBinding

    private lateinit var args: Bundle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        args = Bundle()


        // TODO not sure if these are supposed to be here
        // the button should just take me to the bookmarks page
        val application = requireNotNull(this.activity).application
        val dataSource = QuestionBookmarkDatabase.getInstance(application).questionDatabaseDao

        binding.searchButton.setOnClickListener {
            this.findNavController()
                .navigate(StartFragmentDirections.actionStartFragmentToSearchResultsFragment(binding.searchBox.text.toString()))
        }

        // TODO add a list of predefined searches

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

}