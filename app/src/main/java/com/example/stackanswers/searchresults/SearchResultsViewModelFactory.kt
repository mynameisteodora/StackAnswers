package com.example.stackanswers.searchresults

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchResultsViewModelFactory(
    private val searchQuery: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultsViewModel::class.java)) {
            return SearchResultsViewModel(searchQuery, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}