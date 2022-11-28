package com.sailinghawklabs.exercisetime.screens.exerciseScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.exercisetime.model.Exercise
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.components.ExerciseTimer
import com.sailinghawklabs.exercisetime.util.DefaultExerciseList
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ExerciseViewModel(
) : ViewModel() {

    // timer setup
    private val exerciseTimer = ExerciseTimer(viewModelScope)
    private fun startTimer(timerDuration: Duration, interval: Duration) {
        exerciseTimer.startTimer(timerDuration = timerDuration, interval = interval)
    }
    private fun cancelTimer() = exerciseTimer::cancelTimer
    val isTimerRunning = exerciseTimer.isTimerRunning

    companion object {
        val READY_WAIT: Duration = 10.seconds
        val TIMER_INTERVAL: Duration = 200.milliseconds
    }

    // observable states
    var exerciseList: List<Exercise>? by mutableStateOf(null)
        private set

    var exerciseState: ExerciseState by mutableStateOf(ExerciseState.Idle)
        private set

    var exerciseImageId: Int? by mutableStateOf(null)
        private set

    var timerPrompt by mutableStateOf("")
        private set

    var activeExerciseIndex: Int? by mutableStateOf(null)
        private set

    private var activeExercise: Exercise? = null

    val elapsedTime: MutableState<Duration> = exerciseTimer.elapsedTime

    init {
        getExerciseList()
    }

    private fun setupForExercise(index: Int) {
        if (exerciseList != null) {
            activeExerciseIndex = index
            activeExercise = exerciseList!![activeExerciseIndex!!]
            exerciseImageId = activeExercise!!.imageResourceId
            timerPrompt = "Get ready for Exercise:\n${activeExercise!!.name}"
            startTimer(READY_WAIT, TIMER_INTERVAL)
        }
    }


    fun onStateChanged(newState: ExerciseState) {
        when (newState) {

            ExerciseState.ReadyToStart -> {

            }
            ExerciseState.Exercising -> {


            }
            ExerciseState.Resting -> {

            }

            ExerciseState.Idle -> {
                cancelTimer()
            }
        }
        exerciseState = newState
    }


    private fun getExerciseList() {
        // repository.getExerciseList() eventually
        viewModelScope.launch {
            exerciseList = DefaultExerciseList
            setupForExercise(0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }
}