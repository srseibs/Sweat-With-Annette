package com.sailinghawklabs.sweatwithannette.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sailinghawklabs.sweatwithannette.data.local.entity.ActiveSet
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.SessionEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkoutDao {

    // Session History ----------------------------------------------------------
    @Insert
    suspend fun insertSession(history: SessionEntity)

    @Delete
    suspend fun deleteWorkout(history: SessionEntity)

    @Query("SELECT * FROM ${SessionEntity.TABLE_NAME}")
    fun fetchAllWorkouts(): Flow<List<SessionEntity>>

    // Master list of Exercises ---------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMasterExerciseList(masterExercises: List<ExerciseMasterEntity>)

    @Query("SELECT * FROM ${ExerciseMasterEntity.TABLE_NAME}")
    fun getMasterExerciseList(): Flow<List<ExerciseMasterEntity>>

    @Query("SELECT * FROM ${ExerciseMasterEntity.TABLE_NAME} WHERE id = :masterIndex")
    suspend fun getMasterExercise(masterIndex: Int): ExerciseMasterEntity

    @Query("DELETE FROM ${ExerciseMasterEntity.TABLE_NAME}")
    suspend fun deleteMasterExerciseList()

    // Custom workouts -------------------------------------------------------
    @Insert
    suspend fun insertWorkout(workoutSet: WorkoutEntity)

    @Query("DELETE FROM ${WorkoutEntity.TABLE_NAME} WHERE name = :workoutName")
    suspend fun deleteWorkout(workoutName: String)

    @Query("UPDATE ${WorkoutEntity.TABLE_NAME} SET exercises = :exercises WHERE name = :workoutName")
    suspend fun updateWorkout(workoutName: String, exercises: List<Int>): Int

    @Transaction
    suspend fun addOrUpdateWorkout(workoutEntity: WorkoutEntity) {
        if (updateWorkout(workoutEntity.name, workoutEntity.exercises) == 0) {
            insertWorkout(workoutEntity)
        }
    }

    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME} WHERE name = :workoutName")
    suspend fun getWorkout(workoutName: String): WorkoutEntity

    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME}")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveWorkout(activeSet: ActiveSet)

    @Query("SELECT setName FROM ${ActiveSet.TABLE_NAME}")
    fun getActiveWorkout(): Flow<List<String?>>

}
