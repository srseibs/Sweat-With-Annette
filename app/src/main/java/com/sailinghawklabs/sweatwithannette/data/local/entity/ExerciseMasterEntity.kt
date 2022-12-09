package com.sailinghawklabs.sweatwithannette.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = ExerciseMasterEntity.TABLE_NAME)
data class ExerciseMasterEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val imageResource: Int,

){
    companion object {
        const val TABLE_NAME = "exercise_master"
    }
}


