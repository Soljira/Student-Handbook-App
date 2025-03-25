package com.example.studenthandbookapp.marketing

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@RequiresApi(Build.VERSION_CODES.R)
@androidx.annotation.OptIn(UnstableApi::class)
class VideoPlayerActivity : ComponentActivity() {

    private var player: ExoPlayer? = null  // Declare ExoPlayer instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get video URI from intent
        val videoUri = intent.getStringExtra("videoUri")?.let { Uri.parse(it) }

        // Create ExoPlayer instance
        player = ExoPlayer.Builder(this).build().apply {
            videoUri?.let {
                setMediaItem(MediaItem.fromUri(it))
                prepare()
                playWhenReady = true
            }
        }

        // Create PlayerView and set player
        val playerView = PlayerView(this).apply {
            player = this@VideoPlayerActivity.player
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            useController = true
        }

        setContentView(playerView)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Release the player when the activity is destroyed
        player?.release()
        player = null  // Prevent memory leaks

        // Show system UI again when exiting
        window.insetsController?.show(WindowInsets.Type.systemBars())
    }
}
