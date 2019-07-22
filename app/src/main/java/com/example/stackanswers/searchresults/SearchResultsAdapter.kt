package com.example.stackanswers.searchresults

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stackanswers.databinding.ListItemQuestionBinding
import com.example.stackanswers.network.Question

class SearchResultsAdapter : ListAdapter<Question, SearchResultsAdapter.QuestionViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsAdapter.QuestionViewHolder {
        return QuestionViewHolder(ListItemQuestionBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchResultsAdapter.QuestionViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.question_id == newItem.question_id
        }
    }

    class QuestionViewHolder(private var binding: ListItemQuestionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            binding.question = question
            binding.executePendingBindings()
        }
    }
}