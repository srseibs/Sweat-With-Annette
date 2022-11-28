package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun CircularProgress(
    remainingTimeInMillis: Long,
    maxTimeTimeInMillis: Long,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall,
    ringColor: Color = MaterialTheme.colorScheme.secondary,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary
) {

    var percentRemaining by remember { mutableStateOf(1f) }

    var firstTime by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = remainingTimeInMillis) {
        percentRemaining =
            (remainingTimeInMillis.toFloat() / maxTimeTimeInMillis.toFloat())
                .coerceAtLeast(0.01f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
    ) {

        Box(
            modifier = Modifier
                .background(ringColor, shape = CircleShape)
                .fillMaxSize(1.0f)
        )

        Box(
            modifier = Modifier
                .background(backgroundColor, shape = CircleShape)
                .fillMaxSize(0.95f)
        )

        val animatedPercentRemaining by animateFloatAsState(
            targetValue = percentRemaining,
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearEasing,
            )
        )

        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .aspectRatio(1f),
            color = ringColor,
            strokeWidth = 5.dp,
            progress = animatedPercentRemaining
        )


        Box(
            modifier = Modifier
                .background(backgroundColor, shape = CircleShape)
                .fillMaxSize(0.85f)
        )

        Box(
            modifier = Modifier
                .background(ringColor, shape = CircleShape)
                .fillMaxSize(0.80f)
        )

        Text(
            text = ((percentRemaining * maxTimeTimeInMillis) / 1000).roundToInt().toString(),
            style = textStyle,
            color = backgroundColor,
        )

    }
}


@Preview
@Composable
fun CircularProgressPreview() {

    ExerciseTimeTheme {

        CircularProgress(
            modifier = Modifier.size(240.dp),
            maxTimeTimeInMillis = 10000,
            remainingTimeInMillis = 4000,
        )

    }
}