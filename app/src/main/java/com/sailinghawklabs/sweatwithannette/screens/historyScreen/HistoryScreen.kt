package com.sailinghawklabs.sweatwithannette.screens.historyScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.sweatwithannette.domain.model.Workout
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components.AreYouSureDialog
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {

    var showAlertDialog by remember { mutableStateOf(false) }
    var workoutToDelete: Workout? by remember { mutableStateOf(null) }


    if (showAlertDialog) {
        if (workoutToDelete != null) {
            AreYouSureDialog(
                title = "Delete Workout?",
                detail = "Are you sure you want to delete this workout from history?",
                onConfirm = {
                    showAlertDialog = false
                    historyViewModel.deleteWorkout(workoutToDelete!!)
                    workoutToDelete = null
                },
                onDismiss = {
                    showAlertDialog = false
                    workoutToDelete = null
                }
            )

        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = "Exercise History",
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
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (historyViewModel.workoutHistory.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No Workout History")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(historyViewModel.workoutHistory) { it ->
                    Row(
                        modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        HistoryItem(
                            workout = it,
                            onDelete = {
                                workoutToDelete = it
                                showAlertDialog = true
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    workout: Workout,
    background: Color = MaterialTheme.colorScheme.tertiaryContainer,
    onDelete: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column() {
            if (workout.complete)
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Workout done"
                )
            else
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Workout not done"
                )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(text = workout.date)
            Text(text = workout.setName)
        }
        Column {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}


@Preview
@Composable
fun HistoryScreenPreview() {
    SweatAnnetteTheme {
        HistoryItem(
            workout = Workout(
                id = 0,
                date = "Tue 6-Dec-2022 4:09:31 PM",
                complete = true,
                setName = "Default Workout"
            ),
            onDelete = {},
        )

    }
}