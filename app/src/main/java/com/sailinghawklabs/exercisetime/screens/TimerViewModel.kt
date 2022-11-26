package com.sailinghawklabs.exercisetime.screens

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*


class TimerViewModel : ViewModel() {

    private var timer: Timer = Timer()
    private var initialTimeMillis: Long = 0

    var elapsedTimeMillis = MutableStateFlow<Long>(0)
        private set

    var timerRunning by mutableStateOf(false)

    fun startTimer(timeInMillis: Long, periodMillis: Long = 1000) {
        initialTimeMillis = SystemClock.elapsedRealtime()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val localElapsedTime: Long =
                    (SystemClock.elapsedRealtime() - initialTimeMillis)
                if (localElapsedTime >= timeInMillis)
                    timerRunning = false
                viewModelScope.launch {
                    elapsedTimeMillis.emit(localElapsedTime)
                }
            }
        }, periodMillis, periodMillis)
        timerRunning = true
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}