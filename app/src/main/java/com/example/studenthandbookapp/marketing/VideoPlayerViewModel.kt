package com.example.studenthandbookapp.marketing

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class VideoPlayerViewModel : ViewModel() {
    var exoPlayer: ExoPlayer? = null

    fun initializePlayer(context: Context, uri: Uri) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
                playWhenReady = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}
