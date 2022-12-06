package com.sailinghawklabs.exercisetime.di

import android.content.Context
import androidx.room.Room
import com.sailinghawklabs.exercisetime.data.local.WorkoutDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext appContext: Context): WorkoutDatabase =
        Room.databaseBuilder(appContext, WorkoutDatabase::class.java, "history.db").build()

    @Provides
    @Singleton
    fun provideHistoryDao(db: WorkoutDatabase) =
        db.historyDao()

}