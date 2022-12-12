package com.sailinghawklabs.sweatwithannette.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ActiveSet.TABLE_NAME)
data class ActiveSet(

    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,

    val setName: String

) {
    companion object {
        const val TABLE_NAME = "active_workout_set"
    }
}