package com.sailinghawklabs.sweatwithannette.screens.welcomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: WorkoutHistoryRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.setupDefaults()

            getWorkoutSetName()
        }


    }

    val workoutSetName = MutableStateFlow("")

    private fun getWorkoutSetName() = viewModelScope.launch {
        workoutSetName.value = repository.getActiveWorkoutSetName()
    }

}