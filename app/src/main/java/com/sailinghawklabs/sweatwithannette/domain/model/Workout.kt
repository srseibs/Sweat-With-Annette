package com.sailinghawklabs.sweatwithannette.domain.model

data class Workout(
    val id: Long,
    val date: String,
    val setName:String,
    val complete: Boolean,
)
