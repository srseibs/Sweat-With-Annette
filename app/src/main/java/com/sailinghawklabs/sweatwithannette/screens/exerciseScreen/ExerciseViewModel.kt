package com.sailinghawklabs.sweatwithannette.screens.exerciseScreen

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.R
import com.sailinghawklabs.sweatwithannette.domain.WorkoutRepository
import com.sailinghawklabs.sweatwithannette.domain.model.Exercise
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components.ExerciseTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Closeable
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: WorkoutRepository,
) : ViewModel(), DefaultLifecycleObserver {

    companion object {
        private const val debug = true
        val REST_TIME: Duration = if (debug) 5.seconds else 10.seconds
        val EXERCISE_TIME: Duration = if (debug) 6.seconds else 12.seconds
        val TIMER_INTERVAL: Duration = 200.milliseconds
    }

    // MediaPlayer ===================================================
    private var mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener { reset() }
    }

    fun playSound(assetFileDescriptor: AssetFileDescriptor) {
        mediaPlayer.run {
            reset()
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.declaredLength
            )
            prepareAsync()
        }
    }

    // TextToSpeech =================================================
    private var textToSpeech: TextToSpeech? = null
    fun textToSpeech(context: Context, text: String) {

        textToSpeech = TextToSpeech(context) {

            viewModelScope.launch {
                if (it == TextToSpeech.SUCCESS) {
                    textToSpeech?.let { txtToSpeech ->

                        delay(600) // to allow sounds to play first

                        txtToSpeech.speak(
                            text,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }

    // Exercise Timer ===============================================
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

    fun pauseTimer() = exerciseTimer.pauseTimer(true)
    fun resumeTimer() = exerciseTimer.pauseTimer(false)

    // observable states ===================================================
    var exerciseList: List<Exercise> by mutableStateOf(emptyList())
        private set

    var workoutSetName: String by mutableStateOf("")
        private set

    var exerciseImageId: Int? by mutableStateOf(null)
        private set

    var textPrompt by mutableStateOf("")
        private set

    var textValue by mutableStateOf("")
        private set

    var spokenPrompt by mutableStateOf("")
        private set

    var playSound: Int? by mutableStateOf(null)
        private set

    var exercisesComplete by mutableStateOf(0)
        private set

    var allDoneWithExercises by mutableStateOf(false)
        private set


    private var activeExercise: Exercise? = null
    private var exerciseState: ExerciseState by mutableStateOf(ExerciseState.None)

    val elapsedTime: MutableState<Duration> = exerciseTimer.elapsedTime
    val timeDuration: MutableState<Duration> = exerciseTimer.timerDuration

    private fun resetState() {
        exerciseState = ExerciseState.None
    }

    private fun advanceToNextState() {
        exerciseState = when (exerciseState) {
            ExerciseState.None -> {
                allDoneWithExercises = false
                ExerciseState.ReadyToStart
            }
            ExerciseState.ReadyToStart -> {
                exercisesComplete = 0
                ExerciseState.Exercising
            }
            ExerciseState.Exercising -> {
                exercisesComplete++
                if (exercisesComplete >= exerciseList.size) {
                    ExerciseState.Complete
                } else
                    ExerciseState.Resting
            }
            ExerciseState.Resting -> {
                ExerciseState.Exercising
            }
            ExerciseState.Complete -> {
                ExerciseState.Complete
            }
        }
        setupState(exerciseState)
    }

    private fun setupState(newState: ExerciseState) {
        Log.d("ExerciseViewModel", "setupState: #complete $exercisesComplete")

        when (newState) {
            ExerciseState.None -> {}

            ExerciseState.ReadyToStart -> {
                exercisesComplete = 0
                activeExercise = exerciseList[exercisesComplete]
                exerciseImageId = null
                textPrompt = "Get ready for:"
                textValue = activeExercise!!.name
                spokenPrompt = "Get ready for ${activeExercise!!.name}"
                startTimer(REST_TIME, TIMER_INTERVAL)
            }

            ExerciseState.Exercising -> {
                playSound = null
                exerciseImageId = activeExercise!!.imageResourceId
                textPrompt = "Exercise:"
                textValue = activeExercise!!.name
                startTimer(EXERCISE_TIME, TIMER_INTERVAL)
                spokenPrompt = activeExercise!!.name
            }

            ExerciseState.Resting -> {
                playSound = R.raw.done_sound
                activeExercise = exerciseList[exercisesComplete]
                exerciseImageId = null
                textPrompt = "Get ready for:"
                textValue = activeExercise!!.name
                spokenPrompt = "Get ready for ${activeExercise!!.name}"
                startTimer(REST_TIME, TIMER_INTERVAL)
            }

            ExerciseState.Complete -> {
                playSound = R.raw.done_sound
                allDoneWithExercises = true
            }
        }
    }

    private fun getExerciseList() {
        // repository.getExerciseList() eventually
        viewModelScope.launch {
            repository.getActiveWorkoutSetName().let { activeWorkoutSetName ->
                if (activeWorkoutSetName.isBlank()) {
                    throw Exception("Workout Set name is empty.")
                }
                repository.getWorkoutSet(activeWorkoutSetName).let {
                    exerciseList = it.exerciseList
                    workoutSetName = activeWorkoutSetName
                    resetState()
                    advanceToNextState()
                }
            }
        }
    }

    fun stopBackground() {
        Log.d("ExerciseViewModel", "stopBackground")
        cancelTimer()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        resetState()
    }

    override fun onCleared() {
        Log.d("ExerciseViewModel", "onCleared")
        stopBackground()
        super.onCleared()

    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("ExerciseViewModel", "onPause")
        stopBackground()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d("ExerciseViewModel", "onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d("ExerciseViewModel", "onDestroy")
    }

    override fun addCloseable(closeable: Closeable) {
        super.addCloseable(closeable)
    }

    init {
        getExerciseList()
    }


}

