package com.sailinghawklabs.sweatwithannette.domain.model

data class Workout(
    val date: String,
    val setName:String = "Default Set",
    val complete: Boolean,
)
