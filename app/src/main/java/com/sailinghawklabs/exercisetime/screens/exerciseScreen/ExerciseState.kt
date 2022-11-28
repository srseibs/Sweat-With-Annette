package com.sailinghawklabs.exercisetime.screens.exerciseScreen

sealed class ExerciseState {
    object Idle: ExerciseState()
    object ReadyToStart : ExerciseState()
    object Exercising : ExerciseState()
    object Resting : ExerciseState()
}
