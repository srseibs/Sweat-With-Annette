package com.sailinghawklabs.exercisetime.screens.exerciseScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.components.TimerViewModel

class ExerciseViewModel(
): ViewModel() {
    val timerViewModel: TimerViewModel = viewModel(),


}