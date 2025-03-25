package com.example.studenthandbookapp.marketing

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@RequiresApi(Build.VERSION_CODES.R)
@androidx.annotation.OptIn(UnstableApi::class)
class VideoPlayerActivity : ComponentActivity() {

    private val viewModel: VideoPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        hideSystemUI() // Hide status & navigation bars

        val playerView = PlayerView(this).apply {
            player = viewModel.getPlayer(this@VideoPlayerActivity, intent.getStringExtra("videoUri"))
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            useController = true

            // Hide unwanted buttons
            controllerAutoShow = true
            setShowNextButton(false)
            setShowPreviousButton(false)
            setShowFastForwardButton(false)
            setShowRewindButton(false)

            setOnClickListener { hideSystemUI() } // Tap to re-hide UI if it appears
        }

        setContentView(playerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            viewModel.releasePlayer()
        }
        showSystemUI() // Restore UI when exiting
    }

    private fun hideSystemUI() {
        window.insetsController?.let {
            it.hide(WindowInsets.Type.systemBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        window.insetsController?.show(WindowInsets.Type.systemBars())
    }
}

// ViewModel to retain ExoPlayer instance
class VideoPlayerViewModel : ViewModel() {
    private var player: ExoPlayer? = null
    private var lastPosition: Long = 0
    private var lastUri: String? = null
    private var wasPlaying: Boolean = true

    fun getPlayer(activity: ComponentActivity, videoUri: String?): ExoPlayer {
        if (player == null || lastUri != videoUri) {
            lastUri = videoUri
            player = ExoPlayer.Builder(activity).build().apply {
                videoUri?.let {
                    setMediaItem(MediaItem.fromUri(Uri.parse(it)))
                    prepare()
                    seekTo(lastPosition) // Resume from last known position
                    playWhenReady = wasPlaying // Restore play state
                }
            }
        }
        return player!!
    }

    fun releasePlayer() {
        player?.let {
            lastPosition = it.currentPosition
            wasPlaying = it.playWhenReady
            it.release()
        }
        player = null
    }
}
