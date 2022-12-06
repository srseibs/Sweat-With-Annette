package com.sailinghawklabs.exercisetime.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sailinghawklabs.exercisetime.data.local.WorkoutEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insertWorkout(history: WorkoutEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    fun fetchAllWorkouts() : Flow<List<WorkoutEntity>>


}