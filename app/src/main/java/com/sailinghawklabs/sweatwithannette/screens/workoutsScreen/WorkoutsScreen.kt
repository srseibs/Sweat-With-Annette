package com.sailinghawklabs.sweatwithannette.screens.workoutsScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.sweatwithannette.domain.model.WorkoutSet
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutsViewModel = hiltViewModel(),
    goBack: () -> Unit = {},
    goToWorkOutEdit: (String?) -> Unit,
) {

    val workoutSets = viewModel.workOutSets
    val selectedSetName = viewModel.workoutSetName
    Log.d("WorkoutsScreen", "selectedSet = $selectedSetName")

    if (selectedSetName.isEmpty()) {
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = "Workout Manager",
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
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { goToWorkOutEdit(null) },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = "Create new Workout",
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {

            Text(text = "Selected Workout :    $selectedSetName")
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(workoutSets) { workoutSet ->
                    val selected =  selectedSetName == workoutSet.name
                    WorkoutSetItem(
                        workoutSet = workoutSet,
                        selected = selected,
                        onSelected = { viewModel.selectWorkoutSet(workoutSet.name) },
                        onEdit = { goToWorkOutEdit(workoutSet.name)}
                    )
                    Divider(thickness = 4.dp, color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun WorkoutSetItem(
    modifier: Modifier = Modifier,
    workoutSet: WorkoutSet,
    selected: Boolean,
    onSelected: () -> Unit,
    onEdit:() -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .align(CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = workoutSet.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Row {
                Checkbox(
                    checked = selected,
                    onCheckedChange = { onSelected() }
                )

                IconButton(onClick = { onEdit() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Workout")
                }
            }

        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            LazyRow(
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {
                items(workoutSet.exerciseList) { exercise ->
                    Divider(thickness = 1.dp, color = Color.Black)
                    Column(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(2.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall,
                            text = exercise.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Image(
                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                            contentScale = ContentScale.FillHeight,
                            painter = painterResource(id = exercise.imageResourceId),
                            contentDescription = "Exercise image",
                        )
                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun WorkoutSetItemPreview() {
    SweatAnnetteTheme {
        Surface() {
            WorkoutSetItem(
                selected = true,
                onSelected = {},
                onEdit = {},
                workoutSet = WorkoutSet(
                    name = "Test Workout",
                    exerciseList = DefaultExerciseList,
                )
            )
        }
    }
}