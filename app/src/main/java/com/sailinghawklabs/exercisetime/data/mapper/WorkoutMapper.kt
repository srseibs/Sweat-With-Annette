package com.sailinghawklabs.exercisetime.data.mapper

import com.sailinghawklabs.exercisetime.data.local.WorkoutEntity
import com.sailinghawklabs.exercisetime.domain.model.Workout

fun Workout.toWorkoutEntity() =
    WorkoutEntity(
        date = date,
        complete = complete,
    )


fun WorkoutEntity.toWorkout() =
    Workout(
        date = date,
        complete = complete,
    )