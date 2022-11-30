package com.sailinghawklabs.exercisetime.di

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TextToSpeechModule {
    @Singleton
    @Provides
    fun provideTextToSpeech(@ApplicationContext context: Context) =
        TextToSpeechWithInit(context)
}


class TextToSpeechWithInit(context: Context) {
    var initializedFlow = MutableStateFlow(false)
        private set

    var textToSpeech = TextToSpeech(context) { status ->
        if (status != TextToSpeech.SUCCESS) {
            throw Exception("Text to Speech failed to initialize and returned status: $status")
        }
        initializedFlow.value = true
    }
}