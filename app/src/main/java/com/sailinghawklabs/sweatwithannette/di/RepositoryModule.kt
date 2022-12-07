package com.sailinghawklabs.sweatwithannette.di

import com.sailinghawklabs.sweatwithannette.data.local.WorkoutDao
import com.sailinghawklabs.sweatwithannette.data.local.repository.WorkoutHistoryRepositoryImpl
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
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