package com.sailinghawklabs.sweatwithannette.data.local.entity

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IntListConverters {
    @TypeConverter
    fun fromListInt(list: List<Int>):String = Json.encodeToString(list)

    @TypeConverter
    fun toListInt(value: String):List<Int> = Json.decodeFromString(value)
}


//class ExerciseMasterListConverters {
//
//    @TypeConverter
//    fun fromListExerciseMasterEntity(list: List<ExerciseMasterEntity>) = Json.encodeToString(list)
//
//    @TypeConverter
//    fun toListExerciseMasterEntity(value: String) = Json.decodeFromString<List<ExerciseMasterEntity>>(value)
//
//}