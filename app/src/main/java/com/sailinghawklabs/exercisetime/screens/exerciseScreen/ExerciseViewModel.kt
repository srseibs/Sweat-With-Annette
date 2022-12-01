package com.sailinghawklabs.exercisetime.screens.exerciseScreen

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.exercisetime.R
import com.sailinghawklabs.exercisetime.di.TextToSpeechWithInit
import com.sailinghawklabs.exercisetime.model.Exercise
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.components.ExerciseTimer
import com.sailinghawklabs.exercisetime.util.DefaultExerciseList
import com.sailinghawklabs.exercisetime.util.SoundPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val tts: TextToSpeechWithInit,
    private var soundPlayer: SoundPlayer,
) : ViewModel() {

    companion object {
        private const val debug = true
        val REST_TIME: Duration = if (debug) 5.seconds else 10.seconds
        val EXERCISE_TIME: Duration = if (debug) 6.seconds else 12.seconds
        val TIMER_INTERVAL: Duration = 200.milliseconds
    }

    // timer setup .......................................
    private val exerciseTimer = ExerciseTimer(
        coroutineScope = viewModelScope,
        doneCallback = { timerDone() }
    )

    private fun startTimer(timerDuration: Duration, interval: Duration) {
        exerciseTimer.startTimer(timeDuration = timerDuration, interval = interval)
    }

    private fun cancelTimer() = exerciseTimer::cancelTimer

    private fun timerDone() {
        advanceToNextState()
    }

    // observable states
    private var exerciseList: List<Exercise> by mutableStateOf(emptyList())

    var exerciseState: ExerciseState by mutableStateOf(ExerciseState.None)
        private set

    var exerciseImageId: Int? by mutableStateOf(null)
        private set

    var textPrompt by mutableStateOf("")
        private set

    var textValue by mutableStateOf("")
        private set

    var exercisesComplete: Int by mutableStateOf(0)
        private set

    private var activeExercise: Exercise? = null

    val elapsedTime: MutableState<Duration> = exerciseTimer.elapsedTime
    val timeDuration: MutableState<Duration> = exerciseTimer.timerDuration

    private fun startUI() {

        // don't start until the TextToSpeech is ready
        viewModelScope.launch {
            tts.initializedFlow.collect {
                if (it) {
                    startSequence()
                }
            }
        }
    }

    private fun startSequence() {
        getExerciseList()
    }

    private fun resetState() {
        exerciseState = ExerciseState.None
        exercisesComplete = 0
    }

    private fun speak(string: String) {
        tts.textToSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun advanceToNextState() {
        exerciseState = when (exerciseState) {
            ExerciseState.None -> {
                exercisesComplete = 0
                ExerciseState.ReadyToStart
            }
            ExerciseState.ReadyToStart -> {
                exercisesComplete = 0
                ExerciseState.Exercising
            }
            ExerciseState.Exercising -> {
                exercisesComplete++
                ExerciseState.Resting
            }
            ExerciseState.Resting -> {
                ExerciseState.Exercising
            }
        }

        setupState(exerciseState)
    }


    private fun setupState(newState: ExerciseState) {

        when (newState) {
            ExerciseState.None -> {}

            ExerciseState.ReadyToStart -> {
                exercisesComplete = 0
                activeExercise = exerciseList[exercisesComplete]
                exerciseImageId = null
                textPrompt = "Get ready for:"
                textValue = activeExercise!!.name
                speak("Get ready for ${activeExercise!!.name}")
                startTimer(REST_TIME, TIMER_INTERVAL)
            }

            ExerciseState.Exercising -> {
                exerciseImageId = activeExercise!!.imageResourceId
                textPrompt = "Workout:"
                textValue = activeExercise!!.name
                startTimer(EXERCISE_TIME, TIMER_INTERVAL)
                speak(activeExercise!!.name)
            }

            ExerciseState.Resting -> {
                soundPlayer.playSound(R.raw.start)

                activeExercise = exerciseList[exercisesComplete]
                exerciseImageId = null
                textPrompt = "Get ready for:"
                textValue = activeExercise!!.name
                speak("Get ready for ${activeExercise!!.name}")
                startTimer(REST_TIME, TIMER_INTERVAL)
            }
        }
    }


    private fun getExerciseList() {
        // repository.getExerciseList() eventually
        viewModelScope.launch {
            exerciseList = DefaultExerciseList
            resetState()
            advanceToNextState()
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
        tts.textToSpeech.stop()
    }

    init {
        startUI()
    }
}

