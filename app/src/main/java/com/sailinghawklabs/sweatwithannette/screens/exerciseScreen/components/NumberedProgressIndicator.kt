package com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.sweatwithannette.ui.theme.ExerciseTimeTheme

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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(numExercises) { index ->

            val backgroundColor: Color
            val borderColor: Color
            val textColor: Color


            if (index < activeExerciseIndex) {
                backgroundColor = MaterialTheme.colorScheme.secondary
                borderColor = backgroundColor
                textColor = MaterialTheme.colorScheme.onSecondary
            } else if (index == activeExerciseIndex) {
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
                borderColor = MaterialTheme.colorScheme.secondary
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            } else { // index > activeExerciseIndex
                backgroundColor = MaterialTheme.colorScheme.primary
                borderColor = backgroundColor
                textColor = MaterialTheme.colorScheme.onPrimary
            }

            val dotValue = (index + 1).toString()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(backgroundColor, shape = CircleShape)
                    .border(BorderStroke(2.dp, borderColor), shape = CircleShape)
                    .layout() { measurable, constraints ->
                        // Measure the composable
                        val placeable = measurable.measure(constraints)

                        //get the current max dimension to assign width=height
                        val currentHeight = placeable.height
                        val currentWidth = placeable.width
                        var newDiameter = maxOf(currentHeight, currentWidth)
                        if (index == activeExerciseIndex)
                            newDiameter += 12

                        //assign the dimension and the center position
                        layout(newDiameter, newDiameter) {
                            // Where the composable gets placed
                            placeable.placeRelative(
                                (newDiameter - currentWidth) / 2,
                                (newDiameter - currentHeight) / 2
                            )
                        }
                    }
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = dotValue,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = textColor,
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = PIXEL)
@Composable
fun ExerciseProgressIndicatorPreview() {
    ExerciseTimeTheme {
        NumberedProgressIndicator(
            activeExerciseIndex = 3,
            numExercises = 12,
        )
    }
}