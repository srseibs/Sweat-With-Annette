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

    enum class ScreenMode {
        EDIT, ADD, INIT
    }
    sealed class EventToUi {
        data class EntryError(val message: String) : EventToUi()
    }

    private val _errorMessageToUi = MutableSharedFlow<EventToUi>()
    val errorMessageToUi = _errorMessageToUi.asSharedFlow()
    private fun emitMessage(message: String) {
        viewModelScope.launch {
            _errorMessageToUi.emit(EventToUi.EntryError(message))
        }
    }

    var workoutSet by mutableStateOf(emptyWorkoutSet)
        private set

    var screenMode by mutableStateOf(ScreenMode.INIT)
        private set

    private val workoutNameParameter = savedStateHandle["workoutName"] ?: "missing"

    var showSaveButton by mutableStateOf(false)
        private set

    init {
        if (workoutNameParameter != "") {
            loadWorkoutSet(workoutNameParameter)
        }
    }

    fun saveWorkoutSet() {
        workoutSet = workoutSet.copy(
            name = workoutSet.name.trim()
        )

        if (workoutSet.name.isBlank()) {
            emitMessage("Workout Set name cannot be blank! Enter a name.")
            return
        }

        if (workoutSet.name == "Default") {
            emitMessage("Default Workout set cannot be changed. Enter another name.")
            return
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

    fun workoutNameChanged(newName: String) {
        workoutSet = workoutSet.copy(
            name = newName,
        )
        showSaveButton = true
    }


    fun deleteWorkoutSet() {
    }

    fun deleteExerciseFromWorkoutSet(index: Int) {
        val newList = workoutSet.exerciseList.toMutableList()
        newList.removeAt(index)
        workoutSet = workoutSet.copy(
            exerciseList = newList
        )
        showSaveButton = workoutSet.exerciseList.isNotEmpty()
    }


}