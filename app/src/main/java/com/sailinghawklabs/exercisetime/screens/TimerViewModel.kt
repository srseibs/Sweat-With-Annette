package com.sailinghawklabs.exercisetime.screens

import android.os.SystemClock
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds


// https://stackoverflow.com/questions/54827455/how-to-implement-timer-with-kotlin-coroutines

class TimerViewModel : ViewModel() {

    private var initialTime: Duration = 0.seconds
    private var timerJob: Job? = null

    var isTimerRunning: MutableState<Boolean> = mutableStateOf(false)
        private set

    var elapsedTime: MutableState<Duration> = mutableStateOf(1.seconds)
        private set

    fun startTimer(timerDuration: Duration, interval: Duration = 1.seconds) {
        if (isTimerRunning.value)
            return

        initialTime = SystemClock.elapsedRealtime().milliseconds
        isTimerRunning.value = true

        timerJob = viewModelScope.launchPeriodicJob(repeatMillis = interval.inWholeMilliseconds) {

            elapsedTime.value =
                (SystemClock.elapsedRealtime().milliseconds - initialTime)

            if (elapsedTime.value >= timerDuration) {
                cancelTimer()
            }
        }
    }

    private fun cancelTimer() {
        timerJob?.cancel()
        isTimerRunning.value = false
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }

    private fun CoroutineScope.launchPeriodicJob(
        repeatMillis: Long,
        action: () -> Unit
    ) = viewModelScope.launch {
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