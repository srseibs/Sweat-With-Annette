package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun NumberedProgressIndicator(
    modifier: Modifier = Modifier,
    activeExerciseIndex: Int,
    numExercises: Int,
) {
    Log.d(
        "NumberedProgressIndicator",
        "NumberedProgressIndicator: $activeExerciseIndex of $numExercises"
    )

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(count = numExercises) { index ->

            val backgroundColor: Color
            val dotSize: Dp
            val borderColor: Color
            val textColor: Color


            if (index < activeExerciseIndex) {
                backgroundColor = MaterialTheme.colorScheme.secondary
                dotSize = 28.dp
                borderColor = backgroundColor
                textColor = MaterialTheme.colorScheme.onSecondary
            } else if (index == activeExerciseIndex) {
                dotSize = 34.dp
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
                borderColor = MaterialTheme.colorScheme.secondary
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            } else { // index > activeExerciseIndex
                backgroundColor = MaterialTheme.colorScheme.primary
                dotSize = 28.dp
                borderColor = backgroundColor
                textColor = MaterialTheme.colorScheme.onPrimary
            }

            val dotValue = (index + 1).toString()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(backgroundColor)

//                    .clip(CircleShape)

                    .border(
                        width = 2.dp,
                        color = borderColor,
                    )
                    .size(dotSize)
            ) {
                Text(
                    modifier = Modifier.padding(0.dp),
                    text = dotValue,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = textColor,
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ExerciseProgressIndicatorPreview() {
    ExerciseTimeTheme {
        NumberedProgressIndicator(
            activeExerciseIndex = 3,
            numExercises = 12,
        )
    }
}