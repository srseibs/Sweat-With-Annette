package com.sailinghawklabs.exercisetime.screens.welcome.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    textColor:Color = MaterialTheme.colorScheme.contentColorFor(backgroundColor),
    onClicked: () -> Unit
) {
    OutlinedButton(
        modifier = modifier.aspectRatio(1f),
        onClick = onClicked,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColorFor(backgroundColor = backgroundColor )
        ),
        border = BorderStroke(
            width = 6.dp,
            color = borderColor
        )

    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.displaySmall,
            color = textColor,
        )
    }
}


@Preview
@Composable
fun CircleButtonPreview() {
    ExerciseTimeTheme {
        CircleButton(
            buttonText = "Start",
            onClicked = {}
        )
    }
}