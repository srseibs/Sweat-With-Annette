package com.sailinghawklabs.sweatwithannette.domain

import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet

interface WorkoutRepository {

    // pre-populates the database (if needed) with something
    suspend fun setupDefaults()

    // Workout history maintenance
    suspend fun addToWorkoutHistory(workout: Workout)
    suspend fun deleteFromWorkoutHistory(workout: Workout)
    suspend fun getWorkoutHistory() : List<Workout>

    // Master set of all possible exercises
    suspend fun setMasterExerciseList(masterList: List<ExerciseMasterEntity>)
    suspend fun deleteMasterExerciseList()
    suspend fun getMasterExerciseList(): List<ExerciseMasterEntity>
    suspend fun getMasterExercise(masterIndex: Int): ExerciseMasterEntity

    // Workout Sets - user-customizable sets of exercises
    suspend fun addWorkoutSet(workoutSet: WorkoutSet)
    suspend fun deleteWorkoutSet(workoutSetName: String)
    suspend fun getAllWorkoutSets(): List<WorkoutSet>
    suspend fun getWorkoutSet(workoutSetName: String): WorkoutSet
    // ... active set
    suspend fun setActiveWorkoutSetName(workoutSetName: String)
    suspend fun getActiveWorkoutSetName(): String

}