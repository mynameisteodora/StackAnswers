package com.example.stackanswers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber

const val ANSWER_URL = "/answers/"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up Timber logging
        Timber.plant(Timber.DebugTree())

    }

}
