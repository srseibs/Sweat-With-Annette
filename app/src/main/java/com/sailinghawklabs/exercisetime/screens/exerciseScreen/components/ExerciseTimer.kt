package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import android.os.SystemClock
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds


// https://stackoverflow.com/questions/54827455/how-to-implement-timer-with-kotlin-coroutines

class ExerciseTimer(
    private val coroutineScope: CoroutineScope,
    private val doneCallback: (() -> Unit)? = null
) {
    private var initialTime: Duration = 0.seconds
    private var timerJob: Job? = null

    private var isTimerRunning: MutableState<Boolean> = mutableStateOf(false)
    var timerDuration: MutableState<Duration> = mutableStateOf(0.seconds)
    var elapsedTime: MutableState<Duration> = mutableStateOf(1.seconds)
        private set

    fun startTimer(timeDuration: Duration, interval: Duration = 1.seconds) {
        //Log.d("ExerciseTimer", "startTimer: START, isTimerRunning = ${isTimerRunning.value}")
        if (isTimerRunning.value)
            return

        initialTime = SystemClock.elapsedRealtime().milliseconds
        timerDuration.value = timeDuration
        isTimerRunning.value = true

        timerJob = launchPeriodicJob(repeatMillis = interval.inWholeMilliseconds) {
            elapsedTime.value =
                (SystemClock.elapsedRealtime().milliseconds - initialTime)
            //Log.d("ExerciseTimer", "job: elapsed= ${elapsedTime.value.inWholeMilliseconds}")

            if (elapsedTime.value >= timeDuration) {
                cancelTimer()
                //Log.d("ExerciseTimer", "job: DONE")
                doneCallback?.invoke()
            }
        }
    }

    fun cancelTimer() {
        timerJob?.cancel()
        isTimerRunning.value = false
    }

    private fun launchPeriodicJob(
        repeatMillis: Long,
        action: () -> Unit
    ) = coroutineScope.launch {
        if (repeatMillis > 0) {
            while (isActive) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }
}