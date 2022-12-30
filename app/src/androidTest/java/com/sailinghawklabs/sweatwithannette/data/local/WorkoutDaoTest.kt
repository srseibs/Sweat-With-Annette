@file:OptIn(ExperimentalCoroutinesApi::class)

package com.sailinghawklabs.sweatwithannette.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.sailinghawklabs.sweatwithannette.data.local.entity.ActiveSet
import com.sailinghawklabs.sweatwithannette.data.local.entity.SessionEntity
import com.sailinghawklabs.sweatwithannette.data.mapper.toExerciseMasterEntity
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@SmallTest
class WorkoutDaoTest {

    val dummySessionEntity1 = SessionEntity(
        id = 0,
        date = "Monday, not Tuesday",
        setName = "Basic Workout",
        complete = false,
    )

    val dummySessionEntity2 = SessionEntity(
        id = 0,
        date = "Monday, not Tuesday",
        setName = "Hard Workout",
        complete = true,
    )

    val dummyActiveSet1 = ActiveSet(
        id = 0,
        setName = "Dummy Set 1"
    )

    val dummyActiveSet2 = ActiveSet(
        id = 0,
        setName = "Dummy Set 2"
    )


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var workoutDao: WorkoutDao

    @Inject
    @Named("test_db")
    lateinit var database: WorkoutDatabase

    @Before
    fun setup() {
        hiltRule.inject()
        workoutDao = database.workoutDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertWorkout() = runTest(UnconfinedTestDispatcher()) {
        workoutDao.insertWorkoutSession(dummySessionEntity1)
        workoutDao.insertWorkoutSession(dummySessionEntity2)
        val workouts = workoutDao.fetchAllWorkouts().conflate().first()
        assertThat(workouts[0].date).isEqualTo(dummySessionEntity1.date)
        assertThat(workouts[0].setName).isEqualTo(dummySessionEntity1.setName)
        assertThat(workouts[1].date).isEqualTo(dummySessionEntity2.date)
        assertThat(workouts[1].setName).isEqualTo(dummySessionEntity2.setName)
    }

    @Test
    fun deleteWorkout() = runTest(UnconfinedTestDispatcher()) {
        workoutDao.insertWorkoutSession(dummySessionEntity1)
        workoutDao.insertWorkoutSession(dummySessionEntity2)
        val workouts = workoutDao.fetchAllWorkouts().conflate().first()
        assertWithMessage("Inserted two").that(workouts.size).isEqualTo(2)

        workoutDao.deleteWorkout(workouts[1])
        val workouts2 = workoutDao.fetchAllWorkouts().conflate().first()

        assertWithMessage("After deleting 1").that(workouts2.size).isEqualTo(1)

        assertThat(workouts2[0].date).isEqualTo(dummySessionEntity1.date)
        assertThat(workouts2[0].setName).isEqualTo(dummySessionEntity1.setName)
    }

    @Test
    fun setMasterExerciseList() = runTest(UnconfinedTestDispatcher()) {
        val masterList = DefaultExerciseList.map{it.toExerciseMasterEntity()}

        workoutDao.setMasterExerciseList(masterList)
        var masterReadback = workoutDao.getMasterExerciseList().conflate().first()
        assertWithMessage("List added/List retrieved").that(masterReadback.size).isEqualTo(masterList.size)

        val item = workoutDao.getMasterExercise(3)
        assertWithMessage("Item retrieved").that(item).isEqualTo(masterList[2])

        workoutDao.deleteMasterExerciseList()
        masterReadback = workoutDao.getMasterExerciseList().conflate().first()
        assertWithMessage("Deleted list, empty?").that(masterReadback.size).isEqualTo(0)
    }

    @Test
    fun setChangeActiveWorkoutSet() = runTest(UnconfinedTestDispatcher()) {
        workoutDao.setActiveWorkout(dummyActiveSet1)
        val activeSetReadback = workoutDao.getActiveWorkout().conflate().first()
        assertWithMessage("Set and readback Active Set")
            .that(activeSetReadback).isEqualTo(dummyActiveSet1.setName)
    }

}