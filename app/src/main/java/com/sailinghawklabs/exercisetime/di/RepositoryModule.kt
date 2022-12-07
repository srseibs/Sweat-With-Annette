package com.sailinghawklabs.exercisetime.di

import com.sailinghawklabs.exercisetime.data.local.WorkoutDao
import com.sailinghawklabs.exercisetime.data.local.repository.WorkoutHistoryRepositoryImpl
import com.sailinghawklabs.exercisetime.domain.WorkoutHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dao: WorkoutDao): WorkoutHistoryRepository =
        WorkoutHistoryRepositoryImpl(dao)



}