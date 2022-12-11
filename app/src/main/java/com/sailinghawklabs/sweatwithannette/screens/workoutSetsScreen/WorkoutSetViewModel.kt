package com.sailinghawklabs.sweatwithannette.screens.workoutSetsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutSetViewModel @Inject constructor(
    val repository: WorkoutHistoryRepository,
) : ViewModel() {


    var workoutSetName = MutableStateFlow("")
        private set

    var workOutSets = MutableStateFlow<List<WorkoutSet>>(emptyList())
        private set

    init {
        getWorkoutSetName()
        getWorkoutSets()
    }

    private fun getWorkoutSets() = viewModelScope.launch {
        val results = repository.getAllWorkoutSets()
        workOutSets.value = results
    }

    private fun getWorkoutSetName() = viewModelScope.launch {
        workoutSetName.value = repository.getActiveWorkoutSetName()
    }
}