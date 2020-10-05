package com.example.doggo.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.doggo.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var myIntent: Intent
    private lateinit var circularProgress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        circularProgress = findViewById(R.id.progress_circular)
        myIntent = Intent(this, MainActivity::class.java)
        runnable = Runnable {
            startActivity(myIntent)
        }
        handler.postDelayed(runnable, 3000)
    }
}
