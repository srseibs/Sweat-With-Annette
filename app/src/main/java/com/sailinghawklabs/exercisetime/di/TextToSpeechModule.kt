package com.sailinghawklabs.exercisetime.di

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TextToSpeechModule {


    @Singleton
    @Provides
    fun provideTextToSpeech(@ApplicationContext context: Context) =
        TextToSpeechWithInit(context)
}


class TextToSpeechWithInit(private val context: Context) {
    var isInitialized = false // this should be true before calling speak()
    var textToSpeech = TextToSpeech(context) { status ->
        if (status != TextToSpeech.SUCCESS) {
            throw Exception("Text to Speech failed to initialize and returned status: $status")
        }
        isInitialized = true
    }
}