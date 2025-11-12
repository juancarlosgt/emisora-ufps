package com.example.emisoraufps

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class PlayerViewModel : ViewModel() {
    private var player: ExoPlayer? = null
    var isPlaying by mutableStateOf(false)
        private set

    fun initializePlayer(context: Context) {
        if (player == null) {
            player = ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri("https://apps.ufps.edu.co/emisoraufps")
                setMediaItem(mediaItem)
                prepare()
            }
        }
    }

    fun togglePlayback() {
        player?.let { exoPlayer ->
            if (isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
            isPlaying = !isPlaying
        }
    }

    fun play() {
        player?.play()
        isPlaying = true
    }

    fun pause() {
        player?.pause()
        isPlaying = false
    }

    override fun onCleared() {
        player?.release()
        player = null
    }
}