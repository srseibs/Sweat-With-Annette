package com.sailinghawklabs.sweatwithannette.screens.welcomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.data.local.WorkoutDao
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutSetEntity
import com.sailinghawklabs.sweatwithannette.data.mapper.toMasterExerciseEntities
import com.sailinghawklabs.sweatwithannette.domain.WorkoutHistoryRepository
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import com.sailinghawklabs.sweatwithannette.util.DEFAULT_WORKOUT_SET_NAME
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    repository: WorkoutHistoryRepository,
) : ViewModel() {




    init {
        // setup the default exercise lists and sets. Maybe move all of this to the Repository?


        val masterExerciseList = DefaultExerciseList

        viewModelScope.launch(Dispatchers.IO) {

            repository.setMasterExerciseList(masterExerciseList.toMasterExerciseEntities())
            repository.addWorkoutSet(
                workoutSet = WorkoutSet(DEFAULT_WORKOUT_SET_NAME, DefaultExerciseList)
            )
            repository.setActiveWorkoutSetName(DEFAULT_WORKOUT_SET_NAME)

        }
    }

}