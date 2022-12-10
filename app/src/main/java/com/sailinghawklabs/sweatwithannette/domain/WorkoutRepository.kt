package com.sailinghawklabs.sweatwithannette.domain

import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

interface WorkoutHistoryRepository {

    // Workout history maintenance
    suspend fun addWorkout(workout: Workout)
    suspend fun deleteWorkout(workout: Workout)
    suspend fun getWorkoutHistory() : Flow<List<Workout>>

    // Master set of all possible exercises
    suspend fun setMasterExerciseList(masterList: List<ExerciseMasterEntity>)
    suspend fun deleteMasterExerciseList()

    // Workout Sets - user-customizable sets of exercises
    suspend fun addWorkoutSet(workoutSet: WorkoutSet)
    suspend fun deleteWorkoutSet(workoutSetName: String)
    suspend fun setActiveWorkoutSetName(workoutSetName: String)
    suspend fun getActiveWorkoutSetName(): String



}