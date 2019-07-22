package com.example.stackanswers

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stackanswers.network.Question
import com.example.stackanswers.searchresults.SearchResultsAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Question>?) {
    val adapter = recyclerView.adapter as SearchResultsAdapter
    adapter.submitList(data)
}