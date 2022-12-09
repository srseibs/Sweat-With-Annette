package com.sailinghawklabs.sweatwithannette.data.local

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insertWorkout(history: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(history: WorkoutEntity)

    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME} ORDER BY date DESC")
    fun fetchAllWorkouts(): Flow<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMasterExercise(masterExercise: ExerciseMasterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMasterExerciseList(masterExercises: List<ExerciseMasterEntity>)

    @Insert
    suspend fun insertWorkoutSet(workoutSet: WorkoutSetEntity)

    @Update
    suspend fun updateWorkoutSet(workoutSet: WorkoutSetEntity)

    suspend fun insertOrUpdateWorkOutSet(workoutSet: WorkoutSetEntity) {
        try {
            insertWorkoutSet(workoutSet)
        } catch (e: SQLiteConstraintException) {
            updateWorkoutSet(workoutSet)
        }
    }


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