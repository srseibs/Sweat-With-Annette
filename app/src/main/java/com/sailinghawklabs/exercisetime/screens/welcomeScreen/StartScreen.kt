package com.sailinghawklabs.exercisetime.screens.welcomeScreen

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import com.sailinghawklabs.exercisetime.R
import com.sailinghawklabs.exercisetime.screens.welcomeScreen.components.CircleButton
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    goToBmi: () -> Unit,
    goToHistory: () -> Unit,
) {
    StartScreenContent(
        modifier = modifier,
        onStart = onStart,
        goToBmi = goToBmi,
        goToHistory = goToHistory,
    )
}

@Composable
fun StartScreenContent(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    goToBmi: () -> Unit,
    goToHistory: () -> Unit,
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
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

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
                        text = "Calculate"
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
                        text = "History"
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
    ExerciseTimeTheme {
        StartScreenContent(onStart = {}, goToBmi = {}, goToHistory = {})
    }
}