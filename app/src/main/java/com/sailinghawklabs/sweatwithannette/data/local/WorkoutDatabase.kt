package com.sailinghawklabs.sweatwithannette.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sailinghawklabs.sweatwithannette.data.local.entity.ActiveSet
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterListConverters
import com.sailinghawklabs.sweatwithannette.data.local.entity.IntListConverters
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutSetEntity

@Database(
    entities = [
        WorkoutEntity::class,
        WorkoutSetEntity::class,
        ExerciseMasterEntity::class,
        ActiveSet::class,
    ],
    version = 1,
    exportSchema = false,
)

@TypeConverters(
    IntListConverters::class,
    ExerciseMasterListConverters::class
)

abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}