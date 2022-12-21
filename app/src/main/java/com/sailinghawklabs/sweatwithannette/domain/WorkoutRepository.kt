package com.sailinghawklabs.sweatwithannette.domain

import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    // pre-populates the database (if needed) with something
    suspend fun setupDefaults()

    // Workout history maintenance
    suspend fun addToWorkoutHistory(workout: Workout)
    suspend fun deleteFromWorkoutHistory(workout: Workout)
    suspend fun getWorkoutHistory() : Flow<List<Workout>>

    // Master set of all possible exercises
    suspend fun setMasterExerciseList(masterList: List<ExerciseMasterEntity>)
    suspend fun deleteMasterExerciseList()
    suspend fun getMasterExerciseList(): Flow<List<ExerciseMasterEntity>>
    suspend fun getMasterExercise(masterIndex: Int): ExerciseMasterEntity

    // Workout Sets - user-customizable sets of exercises
    suspend fun addWorkoutSet(workoutSet: WorkoutSet)
    suspend fun deleteWorkoutSet(workoutSetName: String)
    suspend fun getAllWorkoutSets(): Flow<List<WorkoutSet>>
    suspend fun getWorkoutSet(workoutSetName: String): Flow<WorkoutSet>
    // ... active set
    suspend fun setActiveWorkoutSetName(workoutSetName: String)
    suspend fun getActiveWorkoutSetName(): Flow<String?>

}