package com.sailinghawklabs.sweatwithannette.domain.model

data class WorkoutSet(
    val name:String,
    val exerciseList: List<Exercise>
)

val emptyWorkoutSet = WorkoutSet("empty", emptyList())



