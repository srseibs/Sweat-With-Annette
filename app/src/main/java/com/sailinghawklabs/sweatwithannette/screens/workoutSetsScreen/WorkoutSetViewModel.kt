package com.sailinghawklabs.sweatwithannette.screens.workoutSetsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutRepository
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutSetViewModel @Inject constructor(
    val repository: WorkoutRepository,
) : ViewModel() {

    var workoutSetName by mutableStateOf("")
        private set

    var workOutSets by mutableStateOf<List<WorkoutSet>>(emptyList())
        private set

    init {
        getWorkoutSetName()
        getWorkoutSets()
    }

    fun selectWorkoutSet(newName: String) {
        workoutSetName = newName
        setWorkoutSetName()
    }

    private fun getWorkoutSets() = viewModelScope.launch {
        repository.getAllWorkoutSets().collect{
            workOutSets = it
        }

    }

    private fun setWorkoutSetName() = viewModelScope.launch {
        repository.setActiveWorkoutSetName(workoutSetName)

    }

    private fun getWorkoutSetName() = viewModelScope.launch {
        repository.getActiveWorkoutSetName().let {it ->
            workoutSetName = it ?: "null"
        }
    }
}