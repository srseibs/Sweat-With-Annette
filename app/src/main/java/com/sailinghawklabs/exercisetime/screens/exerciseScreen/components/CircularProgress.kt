package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun CircularProgress(
    remainingTime: Float,
    modifier: Modifier = Modifier,
    maxTime: Float,
    ringColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color =  MaterialTheme.colorScheme.primaryContainer,
) {


    val percentRemaining: Float = remember {
        ( (maxTime - remainingTime) / maxTime)
            .coerceAtMost(1f)
            .coerceAtLeast(0.01f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
    ) {

        Box(modifier = Modifier
            .background(ringColor, shape = CircleShape)
            .fillMaxSize(1.0f)
        )

        Box(modifier = Modifier
            .background(backgroundColor, shape = CircleShape)
            .fillMaxSize(0.95f)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .aspectRatio(1f),
            color = ringColor,
            strokeWidth = 5.dp,
            progress = percentRemaining
        )

        Box(modifier = Modifier
            .background(backgroundColor, shape = CircleShape)
            .fillMaxSize(0.85f)
        )

        Box(modifier = Modifier
            .background(ringColor, shape = CircleShape)
            .fillMaxSize(0.80f)
        )

        Text(
            text = "Hello",
            style = MaterialTheme.typography.titleLarge,
            color = backgroundColor,
        )

    }

}


@Preview
@Composable
fun CircularProgressPreview() {

    ExerciseTimeTheme() {

        CircularProgress(
            modifier = Modifier.size(240.dp),
            maxTime = 6f,
            remainingTime = 5f,
        )

    }
}