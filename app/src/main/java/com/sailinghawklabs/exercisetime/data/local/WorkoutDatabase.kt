package com.sailinghawklabs.exercisetime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun historyDao(): WorkoutDao

}