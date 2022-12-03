package com.sailinghawklabs.exercisetime.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sailinghawklabs.exercisetime.R
import com.sailinghawklabs.exercisetime.screens.welcome.components.CircleButton
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
) {
    StartScreenContent(
        modifier = modifier,
        onStart = onStart)
}

@Composable
fun StartScreenContent(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
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

        CircleButton(
            modifier = Modifier.fillMaxWidth(0.4f),
            buttonText = "Start",
            onClicked = onStart,
        )

        CircleButton(
            modifier = Modifier.fillMaxWidth(0.30f),
            backgroundColor = MaterialTheme.colorScheme.primary,
            buttonText = "Calculate BMI",
            buttonStyle = MaterialTheme.typography.titleMedium,
            onClicked = {   },
        )
    }
}


@Preview
@Composable
fun StartScreenPreview() {
    ExerciseTimeTheme {
        StartScreenContent(onStart = {})
    }
}