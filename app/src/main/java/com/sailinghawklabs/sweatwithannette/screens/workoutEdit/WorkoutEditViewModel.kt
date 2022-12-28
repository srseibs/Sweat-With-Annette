package com.sailinghawklabs.sweatwithannette.screens.workoutEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.data.mapper.toExercise
import com.sailinghawklabs.sweatwithannette.domain.WorkoutRepository
import com.sailinghawklabs.sweatwithannette.domain.model.emptyWorkoutSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkoutEditViewModel @Inject constructor(
    val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    enum class ScreenMode {
        EDIT, ADD, INIT, DONE
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

    var showDeleteConfirmation by mutableStateOf(false)
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

        if (workoutSet.exerciseList.isEmpty()) {
            emitMessage("Workout must contain at least one exercise. Add exercises.")
            return
        }

        viewModelScope.launch {
            repository.addWorkoutSet(workoutSet)
            screenMode = ScreenMode.DONE
            loadWorkoutSet(workoutSet.name)
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

    private fun loadWorkoutSet(workoutName: String) {
        if (workoutName.isNotEmpty()) {
            viewModelScope.launch {
                repository.getWorkoutSet(workoutName).let {
                    it.let {
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

    fun deletionDialogConfirmed() {
        showDeleteConfirmation = false
        deleteWorkoutSet()
    }

    fun deletionDialogDismissed() {
        showDeleteConfirmation = false
    }

    fun askDeleteWorkoutSet() {
        if (workoutSet.name == "Default") {
            emitMessage("Default Workout set cannot be deleted. Edit and change the name.")
            return
        }
        showDeleteConfirmation = true
    }

    private fun deleteWorkoutSet() {
        if (workoutSet.name == "Default") {
            emitMessage("Default Workout set cannot be deleted. Edit and change the name.")
            return
        }
        viewModelScope.launch {
            repository.deleteWorkoutSet(workoutSet.name)
            screenMode = ScreenMode.DONE
        }
    }

    fun addExercisesToWorkout(exerciseIds: List<Int>) {
        viewModelScope.launch {
            val newList = workoutSet.exerciseList.toMutableList()
            exerciseIds.forEach { newId ->
                val exercise = repository.getMasterExercise(newId).toExercise()
                newList.add(exercise)
            }
            workoutSet = workoutSet.copy(
                exerciseList = newList
            )
        }
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