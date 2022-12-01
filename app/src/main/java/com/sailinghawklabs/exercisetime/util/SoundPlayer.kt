package com.sailinghawklabs.exercisetime.util

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

// MediaPlayer wrapper
//  inspiration from here: https://medium.com/androiddevelopers/deep-dive-mediaplayer-best-practices-feb4d15a66f5
//
class SoundPlayer(
    private val context: Context,
) {
    private var mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener { reset() }
    }

    fun reset() = mediaPlayer.reset()

    fun playSound(@RawRes rawResId: Int) {
        val assetFileDescriptor = context.resources.openRawResourceFd(rawResId) ?: return
        mediaPlayer.run {
            reset()
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.declaredLength
            )
            prepareAsync()
        }
    }
}