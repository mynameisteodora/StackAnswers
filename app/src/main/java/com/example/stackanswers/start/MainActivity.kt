package com.example.stackanswers.start

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.stackanswers.R
import com.example.stackanswers.bookmarks.BookmarksActivity
import com.example.stackanswers.settings.SettingsActivity
import timber.log.Timber

const val ANSWER_URL = "/answers/"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Set up Timber logging
        Timber.plant(Timber.DebugTree())

        supportActionBar!!.elevation = 0F
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        //setHasOptionsMenu(true)

    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.overflow_menu_start, menu)
//    }

    /**
     * This has to live here so that the settings button press always has the same
     * effect no matter what fragment we are in
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.bookmarks -> {
                this.startActivity(Intent(this, BookmarksActivity::class.java))
                return true
            }
            R.id.settings -> {
                this.startActivity(Intent(this, SettingsActivity::class.java))
            }
            else -> super.onOptionsItemSelected(item)

        }
        return super.onOptionsItemSelected(item)
    }

}
