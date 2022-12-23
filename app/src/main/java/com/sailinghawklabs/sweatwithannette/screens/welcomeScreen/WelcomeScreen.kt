package com.sailinghawklabs.sweatwithannette.screens.welcomeScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.sweatwithannette.R
import com.sailinghawklabs.sweatwithannette.screens.welcomeScreen.components.CircleButton
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    goToBmi: () -> Unit,
    goToHistory: () -> Unit,
    goToWorkoutSets: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
) {

    Log.d("WelcomeScreen", "entered")
    StartScreenContent(
        modifier = modifier,
        onStart = onStart,
        goToBmi = goToBmi,
        goToHistory = goToHistory,
        goToWorkoutSets = goToWorkoutSets,
        selectedWorkout = viewModel.workoutSetName
    )
}

@Composable
fun StartScreenContent(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    goToBmi: () -> Unit,
    goToHistory: () -> Unit,
    goToWorkoutSets: () -> Unit,
    selectedWorkout: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        val logoState = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = logoState,
            enter = fadeIn(animationSpec = tween(2000))
                    + slideInHorizontally(animationSpec = tween(2000))
                    + expandIn(animationSpec = tween(2000)),

            ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.start_logo),
                contentDescription = "app logo"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    modifier = Modifier.clickable { goToWorkoutSets() },
                    text = "Workout: ",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier.clickable { goToWorkoutSets() },
                    text = selectedWorkout,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(onClick = goToWorkoutSets) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Icon"
                    )
                }
            }
            CircleButton(
                modifier = Modifier.size(120.dp),
                borderColor = MaterialTheme.colorScheme.secondary,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                buttonStyle = MaterialTheme.typography.headlineSmall,
                buttonText = "Start",
                onClicked = onStart,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically

            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Calculate",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    CircleButton(
                        modifier = Modifier.size(100.dp),
                        backgroundColor = MaterialTheme.colorScheme.tertiary,
                        borderColor = MaterialTheme.colorScheme.secondaryContainer,
                        buttonText = "BMI",
                        buttonStyle = MaterialTheme.typography.headlineMedium,
                        onClicked = goToBmi,
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    CircleButton(
                        modifier = Modifier.size(100.dp),
                        backgroundColor = MaterialTheme.colorScheme.tertiary,
                        borderColor = MaterialTheme.colorScheme.secondaryContainer,
                        buttonText = "",
                        buttonIcon = Icons.Default.DateRange,
                        buttonStyle = MaterialTheme.typography.headlineMedium,
                        onClicked = goToHistory,
                    )
                }
            }


        }


    }
}


@Preview
@Composable
fun StartScreenPreview() {
    SweatAnnetteTheme {
        StartScreenContent(
            onStart = {},
            goToBmi = {},
            goToHistory = {},
            selectedWorkout = "Default",
            goToWorkoutSets = {},
        )
    }
}