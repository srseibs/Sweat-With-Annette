package com.sailinghawklabs.sweatwithannette.data.local.repository

import com.sailinghawklabs.sweatwithannette.data.local.WorkoutDao
import com.sailinghawklabs.sweatwithannette.data.local.entity.ActiveSet
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutSetEntity
import com.sailinghawklabs.sweatwithannette.data.mapper.toMasterExerciseEntities
import com.sailinghawklabs.sweatwithannette.data.mapper.toWorkout
import com.sailinghawklabs.sweatwithannette.data.mapper.toWorkoutEntity
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import com.sailinghawklabs.sweatwithannette.util.DEFAULT_WORKOUT_SET_NAME
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutHistoryRepositoryImpl @Inject constructor(
    val dao: WorkoutDao,
) : WorkoutHistoryRepository {


    override suspend fun setupDefaults() = withContext(Dispatchers.IO) {
        // setup the default exercise lists and sets. Maybe move all of this to the Repository?
        setMasterExerciseList(DefaultExerciseList.toMasterExerciseEntities())
        addWorkoutSet(
            workoutSet = WorkoutSet(DEFAULT_WORKOUT_SET_NAME, DefaultExerciseList)
        )
        val selectedWorkoutSet = dao.getActiveWorkoutSet().setName
        if (selectedWorkoutSet == "") {
            setActiveWorkoutSetName(DEFAULT_WORKOUT_SET_NAME)
        }
    }

    // Workout history functions ---------------------------------------------------------
    override suspend fun addWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        dao.insertWorkout(workout.toWorkoutEntity())
    }

    override suspend fun deleteWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        dao.deleteWorkout(workout.toWorkoutEntity())
    }

    override suspend fun getWorkoutHistory(): Flow<List<Workout>> = withContext(Dispatchers.IO) {
        dao.fetchAllWorkouts().map { workoutEntities ->
            workoutEntities.map { it.toWorkout() }
        }
    }

    // Custom WorkoutSet functions ----------------------------------------------------------------

    override suspend fun addWorkoutSet(workoutSet: WorkoutSet) = withContext(Dispatchers.IO) {
        val workoutSetEntity = WorkoutSetEntity(
            name = workoutSet.name,
            exercises = workoutSet.exerciseList.map { it.id }
        )
        dao.insertOrUpdateWorkOutSet(workoutSetEntity)
    }

    override suspend fun deleteWorkoutSet(workoutSetName: String) {
        // remove this from the selected, and replace with DEFAULT, if needed
        val selectedWorkoutSet = dao.getActiveWorkoutSet().setName
        if (selectedWorkoutSet == workoutSetName) {
            setActiveWorkoutSetName(DEFAULT_WORKOUT_SET_NAME)
        }
        dao.deleteWorkoutSet(workoutSetName)
    }

    override suspend fun setActiveWorkoutSetName(workoutSetName: String) =
        withContext(Dispatchers.IO) {
            dao.setActiveWorkoutSet(ActiveSet(workoutSetName))
        }

    override suspend fun getActiveWorkoutSetName(): String = withContext(Dispatchers.IO) {
        dao.getActiveWorkoutSet().setName
    }

    // Master Exercise List functions -------------------------------------------------------------

    override suspend fun setMasterExerciseList(masterList: List<ExerciseMasterEntity>) =
        withContext(Dispatchers.IO) {
            dao.setMasterExerciseList(masterList)
        }

    override suspend fun deleteMasterExerciseList() = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }
}