package com.example.stackanswers.searchresults

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchResultsViewModelFactory(
    private val query: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultsViewModel::class.java)) {
            return SearchResultsViewModel(query, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}