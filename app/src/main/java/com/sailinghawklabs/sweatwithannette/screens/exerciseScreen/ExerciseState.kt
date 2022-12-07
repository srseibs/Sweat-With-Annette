package com.sailinghawklabs.sweatwithannette.screens.exerciseScreen

sealed class ExerciseState {
    object ReadyToStart : ExerciseState()
    object Exercising : ExerciseState()
    object Resting : ExerciseState()
    object None: ExerciseState()
    object Complete: ExerciseState()
}
