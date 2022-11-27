package com.sailinghawklabs.exercisetime.model

data class Exercise(
    private var id: Int,
    private var name: String,
    private var imageResourceId: Int,
    private var isComplete: Boolean = false,
    private var isActive: Boolean = false,
)