package com.sailinghawklabs.sweatwithannette.data.mapper

import com.sailinghawklabs.sweatwithannette.data.local.WorkoutEntity
import com.sailinghawklabs.sweatwithannette.domain.model.Workout

fun Workout.toWorkoutEntity() =
    WorkoutEntity(
        date = date,
        complete = complete,
        setName = setName,
    )


fun WorkoutEntity.toWorkout() =
    Workout(
        date = date,
        complete = complete,
        setName = setName
    )