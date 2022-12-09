package com.sailinghawklabs.sweatwithannette.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WorkoutEntity.TABLE_NAME)
data class WorkoutEntity(

    @PrimaryKey
    val date: String,
    val setName: String,
    val complete: Boolean,


) {
    companion object {
        const val TABLE_NAME = "workout_table"
    }
}
