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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutHistoryRepositoryImpl @Inject constructor(
    val dao: WorkoutDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : WorkoutHistoryRepository {

    override suspend fun setupDefaults() = withContext(defaultDispatcher) {
        setMasterExerciseList(masterList = DefaultExerciseList.toMasterExerciseEntities())

        addWorkoutSet(
            workoutSet = WorkoutSet(DEFAULT_WORKOUT_SET_NAME, DefaultExerciseList)
        )
        addWorkoutSet(
            workoutSet = WorkoutSet("Aerobics Only", AerobicOnlyExerciseList)
        )
        getActiveWorkoutSetName().collectLatest {
            Log.d("workout", "set name: $it")
            if (it.isNullOrEmpty()) {
                setActiveWorkoutSetName(DEFAULT_WORKOUT_SET_NAME)
            }
        }
    }

    // Workout history functions ---------------------------------------------------------
    override suspend fun addWorkout(workout: Workout) = withContext(defaultDispatcher) {
        dao.insertWorkout(workout.toWorkoutEntity())
    }

    override suspend fun deleteWorkout(workout: Workout) = withContext(defaultDispatcher) {
        dao.deleteWorkout(workout.toWorkoutEntity())
    }

    override suspend fun getWorkoutHistory(): Flow<List<Workout>> = withContext(defaultDispatcher) {
        dao.fetchAllWorkouts().map { workoutEntities ->
            workoutEntities.map { it.toWorkout() }
        }
    }


    // Custom WorkoutSet functions ----------------------------------------------------------------

    override suspend fun addWorkoutSet(workoutSet: WorkoutSet) = withContext(defaultDispatcher) {
        val workoutSetEntity = WorkoutSetEntity(
            name = workoutSet.name,
            exercises = workoutSet.exerciseList.map { it.id }
        )
        dao.insertOrUpdateWorkOutSet(workoutSetEntity)
    }

    override suspend fun deleteWorkoutSet(workoutSetName: String) = withContext(defaultDispatcher) {
        // remove this from the selected, and replace with DEFAULT, if needed
        getActiveWorkoutSetName().collectLatest {
            if (it == workoutSetName) {
                setActiveWorkoutSetName(DEFAULT_WORKOUT_SET_NAME)
            }
            dao.deleteWorkoutSet(workoutSetName)
        }
    }

    override suspend fun setActiveWorkoutSetName(workoutSetName: String) =
        withContext(defaultDispatcher) {
            dao.setActiveWorkoutSet(ActiveSet(setName = workoutSetName))
        }

    override suspend fun getActiveWorkoutSetName() = withContext(defaultDispatcher) {
        dao.getActiveWorkoutSet()
    }

    override suspend fun getAllWorkoutSets(): Flow<List<WorkoutSet>> =
        withContext(defaultDispatcher) {
            dao.getAllWorkoutSet().map { list ->
                list.map {
                    WorkoutSet(
                        name = it.name,
                        exerciseList = it.exercises.map { id ->
                            dao.getMasterExercise(id).toExercise()
                        }
                    )
                }
            }
        }

    override suspend fun getWorkoutSet(workoutSetName: String): Flow<WorkoutSet> =
        withContext(defaultDispatcher) {
            dao.getWorkoutSet(workoutSetName).map { entity ->
                WorkoutSet(
                    name = entity.name,
                    exerciseList = entity.exercises.map {
                        dao.getMasterExercise(it).toExercise()
                    }
                )
            }
        }

    // Master Exercise List functions -------------------------------------------------------------

    override suspend fun setMasterExerciseList(masterList: List<ExerciseMasterEntity>) =
        withContext(defaultDispatcher) {
            dao.setMasterExerciseList(masterList)
        }


    override suspend fun getMasterExerciseList(): Flow<List<ExerciseMasterEntity>> =
        withContext(defaultDispatcher) {
            dao.getMasterExerciseList()
        }

    override suspend fun getMasterExercise(masterIndex: Int): ExerciseMasterEntity =
        withContext(defaultDispatcher) {
            dao.getMasterExercise(masterIndex)
        }

    override suspend fun deleteMasterExerciseList() = withContext(defaultDispatcher) {
        dao.deleteMasterExerciseList()
    }
}