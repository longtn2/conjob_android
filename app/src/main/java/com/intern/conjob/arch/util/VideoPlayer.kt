package com.intern.conjob.arch.util

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

class VideoPlayer {
    companion object {
        var player: ExoPlayer? = null
        var postDetailVideos: MutableList<Int> = mutableListOf()

        fun initializePlayer(context: Context) {
            player?.release()
            player = ExoPlayer.Builder(context)
                .build()
                .also { exoPlayer ->
                    exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
                    exoPlayer.playWhenReady = false
                }
        }

        fun removePostDetailVideo() {
            for (index in postDetailVideos) {
                player?.removeMediaItem(index)
            }
            postDetailVideos.clear()
        }
    }
}
