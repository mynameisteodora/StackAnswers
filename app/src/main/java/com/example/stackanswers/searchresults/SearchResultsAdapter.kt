package com.example.stackanswers.searchresults

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stackanswers.R
import com.example.stackanswers.databinding.ListItemQuestionBinding
import com.example.stackanswers.network.Question
import timber.log.Timber

class SearchResultsAdapter(val clickListener: SearchResultsListener): ListAdapter<Question, SearchResultsAdapter.ViewHolder>(SearchResultsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemQuestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Question, clickListener: SearchResultsListener) {
            binding.question = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemQuestionBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val questionTitle = itemView.findViewById(R.id.question_title) as TextView
//        val questionScore = itemView.findViewById(R.id.question_score) as TextView
//        val boxHolder = itemView.findViewById(R.id.box_holder) as ConstraintLayout
//
//        fun bind(item: Question, clickListener: SearchResultsListener) {
//            questionTitle.text = item.title
//            questionScore.text = "Score: " + item.score.toString()
//            boxHolder.setOnClickListener{
//                clickListener
//            }
//        }
//
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater
//                    .inflate(R.layout.list_item_question, parent, false)
//
//                return ViewHolder(view)
//            }
//        }
//    }

    class SearchResultsListener(val clickListener: (questionId: Int) -> Unit) {

        fun onClick(question:Question) {
            clickListener(question.question_id)
            Timber.i("Clicked on View.")
        }
    }



}

class SearchResultsDiffCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.question_id == newItem.question_id
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }
}


