package com.example.stackanswers.question

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.stackanswers.R
import com.example.stackanswers.database.QuestionBookmark
import com.example.stackanswers.database.QuestionBookmarkDatabase
import com.example.stackanswers.database.QuestionDatabaseDao
import com.example.stackanswers.databinding.FragmentQuestionBinding
import com.example.stackanswers.network.Answer
import com.example.stackanswers.network.Question
import kotlinx.coroutines.*
import timber.log.Timber

class QuestionFragment : Fragment() {
    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    private lateinit var question : Question
    private lateinit var myTopAnswer : Answer
    private lateinit var dataSource : QuestionDatabaseDao
    private lateinit var binding : FragmentQuestionBinding
    private lateinit var newBookmark : QuestionBookmark
    var text : String = "Something"

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
        viewModel.questionInDatabase.observe(this, Observer { questionInDatabase -> text = questionInDatabase.toString() })

        setHasOptionsMenu(true)

        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu_question, menu)

        // check if the question is already in the database
//        checkQuestionInDatabase()
    }

//    private fun checkQuestionInDatabase() {
//        coroutineScope.launch {
//            withContext(Dispatchers.IO) {
//                val result = dataSource.checkItemExists(question.question_id)
//
//                if(result == 1) {
//                    text = "Question exists"
//                    //showToast(text)
//                }
//
//                else {
//                    text = "Question DOESNT exist"
//                    //showToast(text)
//                }
//            }
//
//
//        }
//
//
//    }

//    private fun showToast(text: String) {
//        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.set_bookmark -> onSelectBookmark(item); //Toast.makeText(activity, "Added to bookmarks!", Toast.LENGTH_SHORT).show()
            R.id.share -> shareQuestion()
            else -> Toast.makeText(activity, "Something else pressed", Toast.LENGTH_SHORT).show()
        }

        Timber.i("Pressed bookmarks")
        return super.onOptionsItemSelected(item)
    }

    /**
     * Bookmarking an item
     */

    fun changeBookmarkIcon(item: MenuItem, bookmarkOn: Boolean) {
        if(bookmarkOn) {
            item.setIcon(R.drawable.ic_bookmark_full)
            item.isChecked = false
        }
        else {
            item.setIcon(R.drawable.ic_bookmark)
            item.isChecked = true
        }
    }

    fun onSelectBookmark(item: MenuItem) {

        coroutineScope.launch {
            var bookmarkOn = true

            //TODO here check if the bookmark is already selected

            val newBookmark = QuestionBookmark(question.question_id,
                question.body,
                myTopAnswer.answer_id,
                myTopAnswer.body)

            // it's not seeing this
            if(!item.isChecked) {
                Toast.makeText(activity, "Bookmark was on, now turned off", Toast.LENGTH_SHORT).show()
                remove(newBookmark)

                bookmarkOn = false

            }
            else {
                Toast.makeText(activity, "Bookmark was off, now turned on", Toast.LENGTH_SHORT).show()
                insert(newBookmark)

                bookmarkOn = true

            }
            changeBookmarkIcon(item, bookmarkOn)
        }

    }

    private suspend fun insert(bookmark: QuestionBookmark) {
        withContext(Dispatchers.IO) {
            dataSource.insert(bookmark)
            Timber.i("Inserted new bookmark")
        }
    }

    private suspend fun remove(bookmark: QuestionBookmark) {
        withContext(Dispatchers.IO) {
            dataSource.delete(bookmark.questionId)
            Timber.i("Removed bookmark")
        }

    }

    /**
     * Sharing the question
     */

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
