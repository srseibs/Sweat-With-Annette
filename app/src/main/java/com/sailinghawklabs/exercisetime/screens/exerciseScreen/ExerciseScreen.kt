package com.sailinghawklabs.exercisetime.screens.exerciseScreen

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.components.CircularProgress
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
) {
    ExerciseScreenContent(modifier = modifier, goBack = goBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreenContent(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
) {
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

        var restTimer: CountDownTimer? = null

        val initialTimeSec by remember { mutableStateOf(10.0f) }
        var remainingTimeSec by remember { mutableStateOf(10.0f) }

        LaunchedEffect(key1 = true) {
            restTimer = object: CountDownTimer(
                (initialTimeSec*1000).toLong(),1000){

                override fun onTick(p0: Long) {
                    remainingTimeSec = p0.toFloat()/1000f
                }

                override fun onFinish() {
                    remainingTimeSec = 0f
                }
            }
        }

        LaunchedEffect(key1 = true) {
            restTimer?.start()
        }


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
                modifier = Modifier.size(200.dp),
                remainingTime = remainingTimeSec,
                maxTime = initialTimeSec,
            )
        }


    }
}

/// use a viewModel for the timer?
// https://stackoverflow.com/a/68458856/7589309


@Preview
@Composable
fun ExerciseScreenPreview() {
    ExerciseTimeTheme {
        ExerciseScreen(
            goBack = {},
        )
    }
}