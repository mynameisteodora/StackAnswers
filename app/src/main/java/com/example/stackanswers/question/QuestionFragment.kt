package com.example.stackanswers.question

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.stackanswers.ANSWER_URL
import com.example.stackanswers.R
import com.example.stackanswers.database.QuestionBookmark
import com.example.stackanswers.database.QuestionBookmarkDatabase
import com.example.stackanswers.database.QuestionDatabaseDao
import com.example.stackanswers.databinding.FragmentQuestionBinding
import com.example.stackanswers.network.Answer
import com.example.stackanswers.network.Question
import com.example.stackanswers.network.StackAnswerApi
import com.example.stackanswers.network.StackAnswerApiService
import kotlinx.coroutines.*
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.Exception

class QuestionFragment : Fragment() {
    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    private lateinit var question : Question
    private lateinit var myTopAnswer : Answer
    private lateinit var dataSource : QuestionDatabaseDao
    private lateinit var binding : FragmentQuestionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application

        // Database initialisations - this is where we save questions to the database
        dataSource = QuestionBookmarkDatabase.getInstance(application).questionDatabaseDao

        binding = FragmentQuestionBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        question = QuestionFragmentArgs.fromBundle(arguments!!).question
        val viewModelFactory = QuestionViewModelFactory(question, dataSource, application)
        val viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(QuestionViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.topAnswer.observe(this, Observer { answer -> myTopAnswer = answer })
        viewModel.dataSource

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            //TODO here check if the bookmark is already selected
            R.id.bookmarks -> onSelectBookmark(item); //Toast.makeText(activity, "Added to bookmarks!", Toast.LENGTH_SHORT).show()
            R.id.share -> shareQuestion()
            else -> Toast.makeText(activity, "Something else pressed", Toast.LENGTH_SHORT).show()
        }

        Timber.i("Pressed bookmarks")
        return super.onOptionsItemSelected(item)
    }

    fun changeBookmarkIcon(item: MenuItem) {
        item.setIcon(R.drawable.ic_bookmark_full)
    }

    fun onSelectBookmark(item: MenuItem) {
        coroutineScope.launch {
            val newBookmark = QuestionBookmark(question.question_id,
                question.body,
                myTopAnswer.answer_id,
                myTopAnswer.body)
            insert(newBookmark)
        }
        changeBookmarkIcon(item)
    }

    private suspend fun insert(bookmark: QuestionBookmark) {
        withContext(Dispatchers.IO) {
            dataSource.insert(bookmark)
            Timber.i("Inserted new bookmark")
        }
    }

    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_question, question.share_link))
        return shareIntent
    }

    private fun shareQuestion() {
        startActivity(getShareIntent())
    }


}
