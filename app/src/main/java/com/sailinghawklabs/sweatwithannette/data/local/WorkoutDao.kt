package com.sailinghawklabs.sweatwithannette.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sailinghawklabs.sweatwithannette.data.local.entity.ActiveSet
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkoutDao {

    // Workout History ----------------------------------------------------------
    @Insert
    suspend fun insertWorkout(history: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(history: WorkoutEntity)

    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME} ORDER BY date DESC")
    fun fetchAllWorkouts(): Flow<List<WorkoutEntity>>


    // Master list of Exercises ---------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMasterExerciseList(masterExercises: List<ExerciseMasterEntity>)

    @Query("SELECT * FROM ${ExerciseMasterEntity.TABLE_NAME}")
    fun getMasterExerciseList(): Flow<List<ExerciseMasterEntity>>

    @Query("SELECT * FROM ${ExerciseMasterEntity.TABLE_NAME} WHERE id = :masterIndex")
    suspend fun getMasterExercise(masterIndex: Int): ExerciseMasterEntity

    @Query("DELETE FROM ${ExerciseMasterEntity.TABLE_NAME}")
    suspend fun deleteMasterExerciseList()

    // Custom workout sets -------------------------------------------------------
    @Insert
    suspend fun insertWorkoutSet(workoutSet: WorkoutSetEntity)

    @Query("DELETE FROM ${WorkoutSetEntity.TABLE_NAME} WHERE name = :workoutSetName")
    suspend fun deleteWorkoutSet(workoutSetName: String)

    @Query("UPDATE ${WorkoutSetEntity.TABLE_NAME} SET exercises = :exercises WHERE name = :workoutName")
    suspend fun updateWorkoutSet(workoutName: String, exercises: List<Int>): Int

    @Transaction
    suspend fun addOrUpdateWorkOutSet(workoutSet: WorkoutSetEntity) {
        if (updateWorkoutSet(workoutSet.name, workoutSet.exercises) == 0) {
            insertWorkoutSet(workoutSet)
        }
    }

    @Query("SELECT * FROM ${WorkoutSetEntity.TABLE_NAME} WHERE name = :workoutSetName")
    suspend fun getWorkoutSet(workoutSetName: String): WorkoutSetEntity

    @Query("SELECT * FROM ${WorkoutSetEntity.TABLE_NAME}")
    fun getAllWorkoutSet(): Flow<List<WorkoutSetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveWorkoutSet(activeSet: ActiveSet)

    @Query("SELECT setName FROM ${ActiveSet.TABLE_NAME}")
    fun getActiveWorkoutSet(): String?

}

//     val date: String,
//    val setName: String,
//    val complete: Boolean,
//
//
//) {
//    companion object {
//        const val TABLE_NAME = "WorkoutTable"

// WorkoutTable
// Date                     setName            complete
// 12-Dec-2022 10:11:12 PM   "Mike's Quickie"   true
// 12-Dec-2022 10:54:00 PM   "Mike's Quickie"   true
// 12-Dec-2022 11:33:12 PM   "Default"          false

//ExerciseMasterTable  - how to prepopulate?
//Id   Name               Image
// 1    "Jumping Jacks"    R.id.bbb
// 2    "Lunge"            R.id.xxx
// 3    "Jump"             R.id.xxx
// 4    "Situp"            R.id.xxx
// 5    "Push UP"          R.id.xxx
//
//
//WorkoutSetsTable  - Lists in room need to use DataConverter and GSON
//Id  Name                Exercises
// 1  "Default"           1,2,3,4,5
// 2  "Mike's Quickie"    4,1,3