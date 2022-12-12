package com.sailinghawklabs.sweatwithannette.screens.welcomeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: WorkoutHistoryRepository,
) : ViewModel() {

    init {
        setupEmptyDatabase()
    }

    getWorkoutSetName()

    var _workoutSetName = mutableStateOf("")
    val workoutSetName: State<String> = _workoutSetName

    private fun getWorkoutSetName() = viewModelScope.launch {
        _workoutSetName.value = repository.getActiveWorkoutSetName() ?: ""
    }

    private fun setupEmptyDatabase() {
        viewModelScope.launch {
            repository.setupDefaults()
        }
    }


}