package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun NumberedProgressIndicator(
    modifier: Modifier = Modifier,
    activeExerciseIndex: Int,
    numExercises: Int,
    upcomingColor:Color = MaterialTheme.colorScheme.primary,
    doneColor: Color = MaterialTheme.colorScheme.secondary,
) {
    Log.d("NumberedProgressIndicator", "NumberedProgressIndicator: $activeExerciseIndex of $numExercises")
    
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(count = numExercises) { index->

             val backgroundColor =
                if (index > activeExerciseIndex) upcomingColor else doneColor

            val dotValue = (index + 1).toString()

            val dotSize =
                if (index == activeExerciseIndex) 36.dp else 28.dp

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(backgroundColor)
            ) {
                Text(
                    text = dotValue,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.contentColorFor(backgroundColor)
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