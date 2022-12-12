package com.sailinghawklabs.sweatwithannette.data.local.repository

import android.util.Log
import com.sailinghawklabs.sweatwithannette.data.local.WorkoutDao
import com.sailinghawklabs.sweatwithannette.data.local.entity.ActiveSet
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutSetEntity
import com.sailinghawklabs.sweatwithannette.data.mapper.toExercise
import com.sailinghawklabs.sweatwithannette.data.mapper.toMasterExerciseEntities
import com.sailinghawklabs.sweatwithannette.data.mapper.toWorkout
import com.sailinghawklabs.sweatwithannette.data.mapper.toWorkoutEntity
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import com.sailinghawklabs.sweatwithannette.util.AerobicOnlyExerciseList
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


    override suspend fun setupDefaults() {
        // setup the default exercise lists and sets. Maybe move all of this to the Repository?
        setMasterExerciseList(masterList = DefaultExerciseList.toMasterExerciseEntities() )

        addWorkoutSet(
            workoutSet = WorkoutSet(DEFAULT_WORKOUT_SET_NAME, DefaultExerciseList)
        )
        addWorkoutSet(
            workoutSet = WorkoutSet("Aerobics Only", AerobicOnlyExerciseList)
        )

        val activeWorkoutSet = getActiveWorkoutSetName()
        Log.d("WorkoutHistoryRepositoryImpl", "setupDefaults: activeWorkoutSet = $activeWorkoutSet")

        if (activeWorkoutSet == "")
            setActiveWorkoutSetName(DEFAULT_WORKOUT_SET_NAME)


    }

    // Workout history functions ---------------------------------------------------------
    override suspend fun addWorkout(workout: Workout)  {
        dao.insertWorkout(workout.toWorkoutEntity())
    }

    override suspend fun deleteWorkout(workout: Workout)  {
        dao.deleteWorkout(workout.toWorkoutEntity())
    }

    override suspend fun getWorkoutHistory(): Flow<List<Workout>>  =
        dao.fetchAllWorkouts().map { workoutEntities ->
            workoutEntities.map { it.toWorkout() }
        }


    // Custom WorkoutSet functions ----------------------------------------------------------------

    override suspend fun addWorkoutSet(workoutSet: WorkoutSet)  {
        val workoutSetEntity = WorkoutSetEntity(
            name = workoutSet.name,
            exercises = workoutSet.exerciseList.map { it.id }
        )
        dao.insertOrUpdateWorkOutSet(workoutSetEntity)
    }

    override suspend fun deleteWorkoutSet(workoutSetName: String) {
        // remove this from the selected, and replace with DEFAULT, if needed
        if (getActiveWorkoutSetName() == workoutSetName) {
            setActiveWorkoutSetName(DEFAULT_WORKOUT_SET_NAME)
        }
        dao.deleteWorkoutSet(workoutSetName)
    }

    override suspend fun setActiveWorkoutSetName(workoutSetName: String) {
            dao.setActiveWorkoutSet(ActiveSet(setName = workoutSetName))
        }

    override suspend fun getActiveWorkoutSetName(): String =
        dao.getActiveWorkoutSet() ?: ""


    override suspend fun getAllWorkoutSets(): List<WorkoutSet> {
        val allWorkoutSetEntities = dao.getAllWorkoutSet()
        return allWorkoutSetEntities.map { it ->
            WorkoutSet(
                name = it.name,
                exerciseList = it.exercises.map { id ->
                    dao.getMasterExercise(id).toExercise()
                }
            )
        }
    }

    override suspend fun getWorkoutSet(workoutSetName: String): WorkoutSet  {
        val workoutSetEnity = dao.getWorkoutSet(workoutSetName)

        return WorkoutSet(
            name = workoutSetEnity.name,
            exerciseList = workoutSetEnity.exercises.map {
                dao.getMasterExercise(it).toExercise()
            }
        )
    }


    // Master Exercise List functions -------------------------------------------------------------

    override suspend fun setMasterExerciseList(masterList: List<ExerciseMasterEntity>) =
        dao.setMasterExerciseList(masterList)

    override suspend fun deleteMasterExerciseList() {
        TODO("Not yet implemented")
    }
}