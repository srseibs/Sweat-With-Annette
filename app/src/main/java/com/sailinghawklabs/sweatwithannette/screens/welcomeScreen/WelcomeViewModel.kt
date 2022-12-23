package com.sailinghawklabs.sweatwithannette.screens.welcomeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: WorkoutRepository,
) : ViewModel() {

    var workoutSetName by mutableStateOf("")
        private set

    init {
        setupEmptyDatabase()
        viewModelScope.launch {
            repository.getActiveWorkoutSetName().let {
                workoutSetName = it ?: "null"
                Log.d("WelcomeViewModel", "workoutSetName: $it")
            }
        }
    }

    private fun setupEmptyDatabase() {
        viewModelScope.launch {
            repository.setupDefaults()
        }
    }
}