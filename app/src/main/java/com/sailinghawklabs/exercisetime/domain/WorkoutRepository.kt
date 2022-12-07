package com.sailinghawklabs.exercisetime.domain

import com.sailinghawklabs.exercisetime.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutHistoryRepository {

    suspend fun addWorkout(workout: Workout)

    suspend fun deleteWorkout(workout: Workout)

    suspend fun getWorkoutHistory() : Flow<List<Workout>>

}