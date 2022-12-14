package com.sailinghawklabs.sweatwithannette.screens.historyScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository:WorkoutHistoryRepository,
) : ViewModel() {


    var workoutHistory by mutableStateOf<List<Workout>>(emptyList())
        private set

    private var workoutSetName: String = ""


    init {
        getWorkoutSetName()
        getAllWorkouts()
    }

    private fun getWorkoutSetName() {
        viewModelScope.launch {
            repository.getActiveWorkoutSetName().collect(){
                workoutSetName = it ?: "null"
            }
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addWorkoutToDatabase(completed: Boolean) {
        // Tue 6-Dec-2022 2:12:12 PM
        val formatter = DateTimeFormatter.ofPattern("E d-LLL-yyyy h:mm:ss a")
        val current = LocalDateTime.now().format(formatter)
        viewModelScope.launch {
            repository.addWorkout(
                Workout(
                    date = current,
                    complete = completed,
                    setName = workoutSetName
                )
            )
        }
    }

    private fun getAllWorkouts() {
        viewModelScope.launch {
            repository.getWorkoutHistory().collect { workoutList ->
                workoutHistory = workoutList
            }
        }
    }

}