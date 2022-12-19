package com.sailinghawklabs.sweatwithannette.screens.workoutEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutRepository
import com.sailinghawklabs.sweatwithannette.domain.model.emptyWorkoutSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutSetEditViewModel @Inject constructor(
    val repository: WorkoutRepository,
) : ViewModel() {

    var workoutSet by mutableStateOf(emptyWorkoutSet)
        private set

    var screenMode by mutableStateOf(ScreenMode.ADD)
        private set

    fun saveWorkoutSet() {
    }

    fun loadWorkoutSet(workoutName: String) {
        if (workoutName.isNotEmpty()) {
            viewModelScope.launch {
                repository.getWorkoutSet(workoutName).collect {
                    it?.let{
                        workoutSet = it
                        screenMode = ScreenMode.EDIT
                    }
                }
            }
        } else {
            screenMode = ScreenMode.ADD
        }
    }


    fun deleteWorkoutSet() {
    }

    fun deleteExerciseFromWorkoutSet() {
    }


}