package com.sailinghawklabs.sweatwithannette.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sailinghawklabs.sweatwithannette.data.local.entity.ActiveSet
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.IntListConverters
import com.sailinghawklabs.sweatwithannette.data.local.entity.SessionEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutEntity

@Database(
    entities = [
        SessionEntity::class,
        WorkoutEntity::class,
        ExerciseMasterEntity::class,
        ActiveSet::class,
    ],
    version = 1,
    exportSchema = false,
)

@TypeConverters(
    IntListConverters::class,
)

abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}