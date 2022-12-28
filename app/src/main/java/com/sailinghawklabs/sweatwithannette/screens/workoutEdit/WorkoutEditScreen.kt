package com.sailinghawklabs.sweatwithannette.screens.workoutEdit

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.sweatwithannette.R
import com.sailinghawklabs.sweatwithannette.domain.model.Exercise
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components.AreYouSureDialog
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components.ExercisePickerDialog
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList
import com.sailinghawklabs.sweatwithannette.util.DemoWorkoutSet1
import com.sailinghawklabs.sweatwithannette.util.dragdrop.DragDropColumn
import kotlinx.coroutines.flow.collectLatest


@Composable
fun WorkoutEditScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutEditViewModel = hiltViewModel(),
    goBack: () -> Unit = {},
) {
    Log.d("Screen", "WorkoutEditScreen: ")
    var showSaveAlertDialog by remember { mutableStateOf(false) }
    var showPickerDialog by remember { mutableStateOf(false)}
    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler {
        if (viewModel.showSaveButton) {
            showSaveAlertDialog = true
        } else {
            goBack()
        }
    }

    LaunchedEffect(key1 = viewModel.screenMode) {
        if (viewModel.screenMode == WorkoutEditViewModel.ScreenMode.DONE) {
            Log.d("DBG1", "WorkoutEditScreen: DONE -> goBack()")
            goBack()
        }
    }

    LaunchedEffect(key1 = showSaveAlertDialog) {
        viewModel.errorMessageToUi.collectLatest { message ->
            when (message) {
                is WorkoutEditViewModel.EventToUi.EntryError -> {
                    snackbarHostState.showSnackbar(
                        message = message.message,
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }

    if (showSaveAlertDialog) {
        AreYouSureDialog(
            title = "Unsaved Changes!",
            detail = "Are you sure you want to abandon the changes you made?",
            onConfirm = {
                showSaveAlertDialog = false
                goBack()
            },
            onDismiss = {
                showSaveAlertDialog = false
            }
        )
    }


    if (showPickerDialog) {
        ExercisePickerDialog(
            title = "Select Exercises to Add",
            exerciseList = DefaultExerciseList,
            onSelect = { showPickerDialog = false },
            onCancel = { showPickerDialog = false }
        )
    }

    if (viewModel.showDeleteConfirmation) {
        AreYouSureDialog(
            title = "Delete Workout?",
            detail = "Are you sure you want to delete '${viewModel.workoutSet.name}'?",
            onConfirm = {
                viewModel.deletionDialogConfirmed()
            },
            onDismiss = {
                viewModel.deletionDialogDismissed()
            }
        )
    }

    WorkoutEditScreenContent(
        modifier = modifier,
        goBack = {
            if (viewModel.showSaveButton) {
                showSaveAlertDialog = true
            } else {
                goBack()
            }
        },
        onDeleteWorkout = {
            viewModel.askDeleteWorkoutSet()
        },
        showSave = viewModel.showSaveButton,
        onSavePressed = { viewModel.saveWorkoutSet() },
        workoutSet = viewModel.workoutSet,
        onNameChanged = { viewModel.workoutNameChanged(it) },
        onDeleteExercise = viewModel::deleteExerciseFromWorkoutSet,
        screenMode = viewModel.screenMode,
        swapExercise = viewModel::swapExercises,
        snackbarHostState = snackbarHostState,
        onShowPickerDialog = { showPickerDialog = true }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEditScreenContent(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    showSave: Boolean = true,
    onSavePressed: () -> Unit,
    workoutSet: WorkoutSet,
    screenMode: WorkoutEditViewModel.ScreenMode,
    onNameChanged: (String) -> Unit,
    onDeleteExercise: (index: Int) -> Unit,
    onShowPickerDialog: () -> Unit,
    swapExercise: (from: Int, to: Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    onDeleteWorkout: () -> Unit,
) {

    Log.d("screenMode", "WorkoutEditScreenContent: $screenMode")
    val titleBarText =
        if (screenMode == WorkoutEditViewModel.ScreenMode.EDIT) {
            if (showSave)
                "Edit Workout (changed)"
            else
                "Edit Workout"
        } else
            "Create Workout"

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onShowPickerDialog() },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = "Add Exercise",
                )
            }
        },

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = titleBarText,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back arrow"
                        )
                    }
                },
                actions = {
                    if (showSave) {
                        IconButton(onClick = onSavePressed) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_outline_save_24),
                                contentDescription = "Save Edits"
                            )
                        }
                    }
                    IconButton(
                        onClick = onDeleteWorkout,
                    ) {
                        Icon(

                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete WorkoutSet"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = workoutSet.name,
                onValueChange = { onNameChanged(it) },
                label = { Text("Workout Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (workoutSet.exerciseList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Press the + button to add exercises to this workout",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }

            } else {
                DragDropColumn(
                    items = workoutSet.exerciseList,
                    onSwap = { from, to ->
                        swapExercise(from, to)
                    }
                ) { item, index ->
                    Divider(thickness = 1.dp, color = Color.Black)
                    Spacer(Modifier.height(2.dp))
                    WorkOutEditItem(
                        exercise = item,
                        onDeleteItem = { onDeleteExercise(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun WorkOutEditItem(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    onDeleteItem: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { onDeleteItem() }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Exercise"
            )
        }

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            text = exercise.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Image(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .weight(1f),
//                .fillMaxWidth(.40f),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            painter = painterResource(id = exercise.imageResourceId),
            contentDescription = "Exercise image",
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 8.dp),
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_drag_indicator_24),
                    contentDescription = "Drag handle"
                )
            }
            Text(
                text = "Drag to\nreorder",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic,
            )
        }
    }
}


@Preview
@Composable
fun WorkoutEditScreenPreview() {
    SweatAnnetteTheme {
        WorkoutEditScreenContent(
            workoutSet = DemoWorkoutSet1,
            onNameChanged = {},
            screenMode = WorkoutEditViewModel.ScreenMode.EDIT,
            swapExercise = { _, _ -> },
            onDeleteExercise = { },
            goBack = {},
            onSavePressed = {},
            snackbarHostState = SnackbarHostState(),
            onDeleteWorkout = {},
            onShowPickerDialog =  {},
        )
    }
}