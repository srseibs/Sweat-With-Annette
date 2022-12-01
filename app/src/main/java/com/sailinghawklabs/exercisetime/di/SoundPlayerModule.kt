package com.sailinghawklabs.exercisetime.di

import android.content.Context
import com.sailinghawklabs.exercisetime.util.SoundPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SoundPlayerModule {

    @Provides
    @Singleton
    fun provideSoundPlayer(@ApplicationContext context: Context) =
        SoundPlayer(context = context)
}