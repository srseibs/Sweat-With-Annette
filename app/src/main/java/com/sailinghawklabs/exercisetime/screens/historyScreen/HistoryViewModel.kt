package com.sailinghawklabs.exercisetime.screens.historyScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.exercisetime.domain.WorkoutRepository
import com.sailinghawklabs.exercisetime.domain.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: WorkoutRepository,
): ViewModel() {


    @RequiresApi(Build.VERSION_CODES.O)
    fun addWorkoutToDatabase(completed: Boolean) {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a")
        val current = LocalDateTime.now().format(formatter)
        viewModelScope.launch {
            repository.addWorkout(
                Workout(
                    date = current,
                    complete = completed)
            )
        }
    }

    private fun getAllWorkouts() {
        viewModelScope.launch {
            repository.getAllWorkouts().collect{ workoutList ->
                workoutList.forEach {
                    Log.d("HistoryViewModel", "getAllWorkouts: $it")
                }

            }
        }
    }

}