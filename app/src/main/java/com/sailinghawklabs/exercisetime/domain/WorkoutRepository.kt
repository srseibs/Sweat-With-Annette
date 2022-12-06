package com.sailinghawklabs.exercisetime.domain

import com.sailinghawklabs.exercisetime.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    suspend fun addWorkout(workout: Workout)

    suspend fun getAllWorkouts() : Flow<List<Workout>>

}