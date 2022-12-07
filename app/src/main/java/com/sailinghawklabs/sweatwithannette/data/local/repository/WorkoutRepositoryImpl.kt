package com.sailinghawklabs.sweatwithannette.data.local.repository

import com.sailinghawklabs.sweatwithannette.data.local.WorkoutDao
import com.sailinghawklabs.sweatwithannette.data.mapper.toWorkout
import com.sailinghawklabs.sweatwithannette.data.mapper.toWorkoutEntity
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutHistoryRepositoryImpl @Inject constructor(
    val dao: WorkoutDao,
) : WorkoutHistoryRepository {

    override suspend fun addWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        dao.insertWorkout(workout.toWorkoutEntity())
    }

    override suspend fun deleteWorkout(workout: Workout) {
        dao.deleteWorkout(workout.toWorkoutEntity())
    }

    override suspend fun getWorkoutHistory(): Flow<List<Workout>> = withContext(Dispatchers.IO){
       dao.fetchAllWorkouts().map { workoutEntities ->
           workoutEntities.map { it.toWorkout() }
       }
    }
}