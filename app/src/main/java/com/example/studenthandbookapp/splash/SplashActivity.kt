package com.example.studenthandbookapp.splash

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.databinding.ActivitySplashBinding
import com.example.studenthandbookapp.landingpage.SchoolSelection

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize MediaPlayer with your audio file
        mediaPlayer = MediaPlayer.create(this, R.raw.splash_audio).apply {
            isLooping = false
            start() // Start playing immediately
        }

        // Set initial alpha to 0 for fade-in effect
        binding.splashLogo.alpha = 0f

        // Start combined fade-in animation (5 seconds)
        binding.splashLogo.animate().apply {
            duration = 7000
            alpha(1f)
        }.withEndAction {
            // When animation completes, start SchoolSelection activity
            Intent(this@SplashActivity, SchoolSelection::class.java).also {
                mediaPlayer.release()
                startActivity(it)
            }
            finish()
        }
    }
}