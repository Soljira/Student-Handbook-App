package com.example.studenthandbookapp.splash

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.landingpage.Login

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize MediaPlayer with your audio file
        mediaPlayer = MediaPlayer.create(this, R.raw.splash_audio)
        mediaPlayer.isLooping = false // Don't loop the 5-second audio

        // Start fade-in animation
        startFadeInAnimation()
    }

    private fun startFadeInAnimation() {
        val logo = findViewById<ImageView>(R.id.splashLogo)
        val text = findViewById<TextView>(R.id.splashText)

        // Fade in animation (1.5 seconds)
        logo.animate()
            .alpha(1.0f)
            .setDuration(1500)
            .start()

        text.animate()
            .alpha(1.0f)
            .setDuration(1500)
            .setStartDelay(100) // slight delay
            .withEndAction {
                // Play audio when fade-in completes
                mediaPlayer.start()

                // Schedule fade-out after audio completes (5 seconds)
                Handler(Looper.getMainLooper()).postDelayed({
                    startFadeOutAnimation()
                }, 5000)
            }
            .start()
    }

    private fun startFadeOutAnimation() {
        val splashLayout = findViewById<RelativeLayout>(R.id.splashLayout)

        splashLayout.animate()
            .alpha(0.0f)
            .setDuration(500)
            .withEndAction {
                // Start activity with transition
                val intent = Intent(this, Login::class.java)
                val options = ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                startActivity(intent, options.toBundle())
                finish()
            }
            .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources
        mediaPlayer.release()
    }
}