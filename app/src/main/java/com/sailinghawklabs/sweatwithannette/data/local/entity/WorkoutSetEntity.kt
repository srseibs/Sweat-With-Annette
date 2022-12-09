package com.sailinghawklabs.sweatwithannette.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = WorkoutSetEntity.TABLE_NAME)
data class WorkoutSetEntity(

    @PrimaryKey
    val name: String,

    @TypeConverters(IntListConverters::class)
    val exercises: List<Int>

) {
    companion object {
        const val TABLE_NAME = "custom_sets"
    }
}

