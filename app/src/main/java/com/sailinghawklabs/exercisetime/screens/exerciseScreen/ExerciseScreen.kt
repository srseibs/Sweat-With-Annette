package com.sailinghawklabs.exercisetime.screens.exerciseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.components.ExerciseTimer
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.components.CircularProgress
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
) {
    val viewModel: ExerciseViewModel = viewModel()

    val timerDuration by remember { mutableStateOf( 10.seconds) }

    LaunchedEffect(timerDuration) {
        viewModel.startTimer(
            timerDuration = timerDuration,
            interval = 200.milliseconds)
    }

    val elapsedTime = viewModel.elapsedTime.value

    ExerciseScreenContent(
        modifier = modifier,
        goBack = goBack,
        elapsedTime = elapsedTime,
        isRunning = viewModel.isTimerRunning.value,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreenContent(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    elapsedTime: Duration,
    isRunning:Boolean,
) {

    val exerciseDuration = 10.seconds

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = "Sweat with Annette",
                        style = MaterialTheme.typography.titleLarge
                        )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back arrow")
                    }
                }
            )
        },

    ) {scaffoldPadding ->

        Column(
            modifier = modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Get ready to start",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.size(16.dp))
            CircularProgress(
                textStyle = MaterialTheme.typography.displaySmall,
                modifier = Modifier.fillMaxWidth(0.3f),
                remainingTimeInMillis = exerciseDuration.inWholeMilliseconds - elapsedTime.inWholeMilliseconds,
                maxTimeTimeInMillis = exerciseDuration.inWholeMilliseconds,
            )
        }


    }
}

@Preview
@Composable
fun ExerciseScreenPreview() {
    ExerciseTimeTheme {
        ExerciseScreenContent(
            goBack = {},
            isRunning = false,
            elapsedTime = 10.seconds,
        )
    }
}