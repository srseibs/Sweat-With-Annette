package com.sailinghawklabs.exercisetime.screens.exerciseScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.components.ExerciseTimer
import kotlin.time.Duration

class ExerciseViewModel(
): ViewModel() {

    val exerciseTimer = ExerciseTimer(viewModelScope)
    fun startTimer(timerDuration: Duration, interval: Duration) =
        exerciseTimer.startTimer(timerDuration, interval)
    fun cancelTimer() = exerciseTimer.cancelTimer()
    val isTimerRunning = exerciseTimer.isTimerRunning
    val elapsedTime = exerciseTimer.elapsedTime

}