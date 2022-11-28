package com.sailinghawklabs.exercisetime.model

data class Exercise(
    var id: Int,
    var name: String,
    var imageResourceId: Int,
    var isComplete: Boolean = false,
    var isActive: Boolean = false,
)