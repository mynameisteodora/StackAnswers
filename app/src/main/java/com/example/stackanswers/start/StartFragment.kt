package com.example.stackanswers.start

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stackanswers.R
import com.example.stackanswers.bookmarks.BookmarksActivity
import com.example.stackanswers.database.QuestionBookmarkDatabase
import com.example.stackanswers.databinding.FragmentStartBinding
import com.example.stackanswers.settings.SettingsActivity


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
            hideKeyboard(this.activity!!)
        }

        // TODO add a list of predefined searches

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.bookmarks -> {
                this.startActivity(Intent(this.activity, BookmarksActivity::class.java))
                return true
            }
            R.id.settings -> {
                this.startActivity(Intent(this.activity, SettingsActivity::class.java))
            }
            else -> super.onOptionsItemSelected(item)

        }
        return super.onOptionsItemSelected(item)
    }

    fun hideKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus
        var currentFocusedView = activity.currentFocus
        if(currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}