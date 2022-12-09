package com.sailinghawklabs.sweatwithannette.data.local.entity

import androidx.room.TypeConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IntListConverters {
    @OptIn(ExperimentalSerializationApi::class)
    @TypeConverter
    fun fromListInt(list: List<Int>) = Json.encodeToString(list)

    @OptIn(ExperimentalSerializationApi::class)
    @TypeConverter
    fun toListInt(value: String) = Json.decodeFromString<List<Int>>(value)
}


@OptIn(ExperimentalSerializationApi::class)
class ExerciseMasterListConverters {

    @TypeConverter
    fun fromListExerciseMasterEntity(list: List<ExerciseMasterEntity>) = Json.encodeToString(list)

    @TypeConverter
    fun toListExerciseMasterEntity(value: String) = Json.decodeFromString<List<ExerciseMasterEntity>>(value)

}