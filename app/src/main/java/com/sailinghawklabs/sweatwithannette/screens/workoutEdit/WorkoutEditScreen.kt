package com.sailinghawklabs.sweatwithannette.screens.workoutEdit

import android.util.Log
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme
import com.sailinghawklabs.sweatwithannette.util.DemoWorkoutSet1
import com.sailinghawklabs.sweatwithannette.util.dragdrop.DragDropColumn

enum class ScreenMode {
    EDIT, ADD,
}

@Composable
fun WorkoutEditScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutSetEditViewModel = hiltViewModel(),
    goBack: () -> Unit = {},
) {

    val workoutSet = viewModel.workoutSet
    val screenMode = viewModel.screenMode

    WorkoutEditScreenContent(
        modifier = modifier,
        goBack = goBack,
        workoutSet = workoutSet,
        onNameChanged = { },
        onDeleteExercise = viewModel::deleteExerciseFromWorkoutSet,
        screenMode = screenMode,
        swapExercise = viewModel::swapExercises
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEditScreenContent(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    workoutSet: WorkoutSet,
    screenMode: ScreenMode,
    onNameChanged: (String) -> Unit,
    onDeleteExercise: (index:Int) -> Unit,
    swapExercise: (from:Int, to:Int) -> Unit,
) {

    Log.d("Edit", workoutSet.exerciseList.map{it.id }.toString() )

    val titleBarText = remember {
        if (screenMode == ScreenMode.EDIT)
            "Edit Workout"
        else
            "Create Workout"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = titleBarText,
                        style = MaterialTheme.typography.titleLarge
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save Edits"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
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
                onValueChange = { },
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
                    onSwap = {from, to ->
                        swapExercise(from, to)
                    }
                ) {item, index ->
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                .fillMaxWidth(.40f),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            painter = painterResource(id = exercise.imageResourceId),
            contentDescription = "Exercise image",
        )
        IconButton(onClick = { onDeleteItem() }) {
            Icon(imageVector = Icons.Default.Delete,
            contentDescription = "Delete Exercise")
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
            screenMode = ScreenMode.EDIT,
            swapExercise = { _, _ -> },
            onDeleteExercise = { },
            goBack = {},
        )
    }
}