package com.sailinghawklabs.sweatwithannette.domain

import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutHistoryRepository {

    suspend fun addWorkout(workout: Workout)

    suspend fun deleteWorkout(workout: Workout)

    suspend fun getWorkoutHistory() : Flow<List<Workout>>

}