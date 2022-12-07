package com.sailinghawklabs.sweatwithannette.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sailinghawklabs.sweatwithannette.data.local.WorkoutEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME )
data class WorkoutEntity(

    @PrimaryKey
    val date: String,
    val setName: String,
    val complete: Boolean,


) {
    companion object {
        const val TABLE_NAME = "WorkoutTable"
    }
}
