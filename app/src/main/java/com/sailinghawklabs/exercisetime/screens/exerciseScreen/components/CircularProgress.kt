package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    ringColor: Color = MaterialTheme.colorScheme.secondary,
    backgroundColor: Color =  MaterialTheme.colorScheme.onSecondary
) {

    var percentRemaining by remember { mutableStateOf(1f)}

    LaunchedEffect(key1 = remainingTime) {
        percentRemaining = remainingTime / maxTime
    }

    Log.d("Timer", "CircularProgress: $percentRemaining")

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
            text = remainingTime.toInt().toString(),
            style = MaterialTheme.typography.displaySmall,
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
            maxTime = 10000f,
            remainingTime = 0.0976f,
        )

    }
}