package com.sailinghawklabs.exercisetime.data.local.repository

import com.sailinghawklabs.exercisetime.data.local.WorkoutDao
import com.sailinghawklabs.exercisetime.data.mapper.toWorkout
import com.sailinghawklabs.exercisetime.data.mapper.toWorkoutEntity
import com.sailinghawklabs.exercisetime.domain.WorkoutRepository
import com.sailinghawklabs.exercisetime.domain.model.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    val dao: WorkoutDao,
) : WorkoutRepository {

    override suspend fun addWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        dao.insertWorkout(workout.toWorkoutEntity())
    }

    override suspend fun getAllWorkouts(): Flow<List<Workout>> = withContext(Dispatchers.IO){
       dao.fetchAllWorkouts().map { workoutEntities ->
           workoutEntities.map { it.toWorkout() }
       }
    }
}