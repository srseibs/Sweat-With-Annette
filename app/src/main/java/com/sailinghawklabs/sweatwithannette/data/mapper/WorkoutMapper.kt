package com.sailinghawklabs.sweatwithannette.data.mapper

import com.sailinghawklabs.sweatwithannette.data.local.entity.ExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.data.local.entity.SessionEntity
import com.sailinghawklabs.sweatwithannette.domain.model.Exercise
import com.sailinghawklabs.sweatwithannette.domain.model.Workout

fun Workout.toWorkoutEntity() =
    SessionEntity(
        id = id,
        date = date,
        complete = complete,
        setName = setName,
    )


fun SessionEntity.toWorkout() =
    Workout(
        id = id,
        date = date,
        complete = complete,
        setName = setName
    )

fun ExerciseMasterEntity.toExercise() =
    Exercise(
        id = id,
        name = name,
        imageResourceId = imageResource,
        isComplete = false,
        isActive = false,
    )


fun Exercise.toExerciseMasterEntity() =
    ExerciseMasterEntity(
        id = id,
        name = name,
        imageResource = imageResourceId,
    )

fun List<Exercise>.toMasterExerciseEntities() =
    this.map { exercise ->
        exercise.toExerciseMasterEntity()
    }
