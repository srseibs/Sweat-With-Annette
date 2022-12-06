package com.sailinghawklabs.exercisetime.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sailinghawklabs.exercisetime.data.local.WorkoutEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME )
data class WorkoutEntity(

    @PrimaryKey
    val date: String,

    val complete: Boolean,


) {
    companion object {
        const val TABLE_NAME = "WorkoutTable"
    }
}
