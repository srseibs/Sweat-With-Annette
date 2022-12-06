package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.NEXUS_5
import androidx.compose.ui.tooling.preview.Devices.NEXUS_6P
import androidx.compose.ui.tooling.preview.Devices.NEXUS_7_2013
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Green),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val highlightedDotSize = 32.dp
        val normalDotSize = 28.dp

        repeat(numExercises) { index ->

            val backgroundColor: Color
            val dotSize: Dp
            val borderColor: Color
            val textColor: Color


            if (index < activeExerciseIndex) {
                backgroundColor = MaterialTheme.colorScheme.secondary
                dotSize = normalDotSize
                borderColor = backgroundColor
                textColor = MaterialTheme.colorScheme.onSecondary
            } else if (index == activeExerciseIndex) {
                dotSize = highlightedDotSize
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
                borderColor = MaterialTheme.colorScheme.secondary
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            } else { // index > activeExerciseIndex
                backgroundColor = MaterialTheme.colorScheme.primary
                dotSize = normalDotSize
                borderColor = backgroundColor
                textColor = MaterialTheme.colorScheme.onPrimary
            }

            val dotValue = (index + 1).toString()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dotSize)
                    .background(backgroundColor, shape = CircleShape)
                    .border(BorderStroke(2.dp, borderColor), shape = CircleShape)
                    .layout() { measurable, constraints ->
                        // Measure the composable
                        val placeable = measurable.measure(constraints)

                        //get the current max dimension to assign width=height
                        val currentHeight = placeable.height
                        val currentWidth = placeable.width
                        val newDiameter = maxOf(currentHeight, currentWidth)

                        //assign the dimension and the center position
                        layout(newDiameter, newDiameter) {
                            // Where the composable gets placed
                            placeable.placeRelative(
                                (newDiameter - currentWidth) / 2,
                                (newDiameter - currentHeight) / 2
                            )
                        }
                    }
            ){

                Text(
                    modifier = Modifier
                        .padding(1.dp),
//                        .drawBehind {
//                            drawCircle(
//                                color = borderColor,
//                                radius = dotSize.toPx() + 1.dp.toPx(),
//                            )
//                            drawCircle(
//                                color = backgroundColor,
//                                radius = dotSize.toPx(),
//                            )
//                        },
                    text = dotValue,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = textColor,
                )
            }
        }
    }
}


@Preview(showSystemUi = true, device = NEXUS_5)
@Composable
fun ExerciseProgressIndicatorPreview() {
    ExerciseTimeTheme {
        NumberedProgressIndicator(
            activeExerciseIndex = 3,
            numExercises = 12,
        )
    }
}