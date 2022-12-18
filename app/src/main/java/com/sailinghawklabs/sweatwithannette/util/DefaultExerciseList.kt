package com.sailinghawklabs.sweatwithannette.util

import com.sailinghawklabs.sweatwithannette.R
import com.sailinghawklabs.sweatwithannette.domain.model.Exercise
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet

const val DEFAULT_WORKOUT_SET_NAME = "Default"

val DefaultExerciseList = listOf(
    Exercise(
        id = 1,
        name = "Jumping Jacks",
        imageResourceId = R.drawable.ic_jumping_jacks,
    ),
    Exercise(
        id = 2,
        name = "Wall Sit",
        imageResourceId = R.drawable.ic_wall_sit,
    ),
    Exercise(
        id = 3,
        name = "Push Up",
        imageResourceId = R.drawable.ic_push_up,
    ),
    Exercise(
        id = 4,
        name = "Abdominal Crunch",
        imageResourceId = R.drawable.ic_abdominal_crunch,
    ),
    Exercise(
        id = 5,
        name = "Chair Step-up",
        imageResourceId = R.drawable.ic_step_up_onto_chair,
    ),
    Exercise(
        id = 6,
        name = "Squat",
        imageResourceId = R.drawable.ic_squat,
    ),
    Exercise(
        id = 7,
        name = "Tricep Dip on Chair",
        imageResourceId = R.drawable.ic_triceps_dip_on_chair,
    ),
    Exercise(
        id = 8,
        name = "Plank",
        imageResourceId = R.drawable.ic_plank,
    ),
    Exercise(
        id = 9,
        name = "High-knees Running in Place",
        imageResourceId = R.drawable.ic_high_knees_running_in_place,
    ),
    Exercise(
        id = 10,
        name = "Lunge",
        imageResourceId = R.drawable.ic_lunge,
    ),
    Exercise(
        id = 11,
        name = "Push-up and Rotate",
        imageResourceId = R.drawable.ic_push_up_and_rotation,
    ),
    Exercise(
        id = 12,
        name = "Side Plank",
        imageResourceId = R.drawable.ic_side_plank,
    ),
)

val AerobicOnlyExerciseList = listOf(
    Exercise(
        id = 1,
        name = "Jumping Jacks",
        imageResourceId = R.drawable.ic_jumping_jacks,
    ),
    Exercise(
        id = 3,
        name = "Push Up",
        imageResourceId = R.drawable.ic_push_up,
    ),

    Exercise(
        id = 5,
        name = "Chair Step-up",
        imageResourceId = R.drawable.ic_step_up_onto_chair,
    ),
    Exercise(
        id = 9,
        name = "High-knees Running in Place",
        imageResourceId = R.drawable.ic_high_knees_running_in_place,
    ),

)

val DemoWorkoutSet1 = WorkoutSet(
    name = "Demo Set 1",
    exerciseList = AerobicOnlyExerciseList
)