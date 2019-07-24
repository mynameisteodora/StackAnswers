package com.example.stackanswers.bookmarks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stackanswers.R
import com.example.stackanswers.database.QuestionBookmark
import com.example.stackanswers.database.QuestionBookmarkDatabase
import com.example.stackanswers.database.QuestionDatabaseDao
import kotlinx.coroutines.*
import org.w3c.dom.Text

class Bookmarks : AppCompatActivity() {

    // Set up the coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    private lateinit var dataSource : QuestionDatabaseDao
    private lateinit var bookmarkTitle : TextView
    private var queryResult : QuestionBookmark? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)
        val application = requireNotNull(this).application
        dataSource = QuestionBookmarkDatabase.getInstance(application).questionDatabaseDao

        bookmarkTitle = findViewById<TextView>(R.id.bookmark_title)

        coroutineScope.launch {
            updateTitle()
            bookmarkTitle.text = queryResult!!.questionBody
        }


    }

    private suspend fun updateTitle() {
        withContext(Dispatchers.IO) {
            queryResult = dataSource.getLastSavedQuestion()
        }
    }
}
