package com.sailinghawklabs.sweatwithannette.screens.welcomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.sweatwithannette.data.local.WorkoutDao
import com.sailinghawklabs.sweatwithannette.data.local.entity.WorkoutSetEntity
import com.sailinghawklabs.sweatwithannette.data.mapper.toMasterExerciseEntities
import com.sailinghawklabs.sweatwithannette.util.DEFAULT_WORKOUT_SET_NAME
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    dao: WorkoutDao,
) : ViewModel() {

    init {

        val masterExerciseList = DefaultExerciseList


        viewModelScope.launch(Dispatchers.IO) {
            dao.insertMasterExerciseList(masterExerciseList.toMasterExerciseEntities())

            dao.insertOrUpdateWorkOutSet(
                WorkoutSetEntity(
                    DEFAULT_WORKOUT_SET_NAME,
                    (1..masterExerciseList.size).toList())
            )

            dao.insertOrUpdateWorkOutSet(
                WorkoutSetEntity("My Quickie", listOf<Int>(4,1,3,4))
            )



        }


    }


}