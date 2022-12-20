package com.sailinghawklabs.sweatwithannette.screens.workoutEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.domain.WorkoutRepository
import com.sailinghawklabs.sweatwithannette.domain.model.emptyWorkoutSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutSetEditViewModel @Inject constructor(
    val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    sealed class EventToUi {
        data class EntryError(val message: String): EventToUi()
    }

    private val _errorMessageToUi = MutableSharedFlow<EventToUi>()
    val errorMessageToUi = _errorMessageToUi.asSharedFlow()

    var workoutSet by mutableStateOf(emptyWorkoutSet)
        private set

    var screenMode by mutableStateOf(ScreenMode.ADD)
        private set

    private val passedWorkoutName = savedStateHandle["workoutName"] ?: "missing"

    var showSaveButton by mutableStateOf(false)
        private set

    init {
        if (passedWorkoutName != "") {
            loadWorkoutSet(passedWorkoutName)
        }
    }

    fun saveWorkoutSet() {
        viewModelScope.launch {
            _errorMessageToUi.emit(EventToUi.EntryError("missing something"))
        }
        showSaveButton = false
    }


    fun swapExercises(from: Int, to: Int) {
        val fromExercise = workoutSet.exerciseList[from]
        val toExercise = workoutSet.exerciseList[to]
        val newList = workoutSet.exerciseList.toMutableList()
        newList[from] = toExercise
        newList[to] = fromExercise

        workoutSet = workoutSet.copy(
            exerciseList = newList,
        )
        showSaveButton = true

    }

    fun loadWorkoutSet(workoutName: String) {
        if (workoutName.isNotEmpty()) {
            viewModelScope.launch {
                repository.getWorkoutSet(workoutName).collect {
                    it?.let {
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

    fun deleteExerciseFromWorkoutSet(index: Int) {
        val newList = workoutSet.exerciseList.toMutableList()
        newList.removeAt(index)
        workoutSet = workoutSet.copy(
            exerciseList = newList
        )
        showSaveButton = true

    }


}