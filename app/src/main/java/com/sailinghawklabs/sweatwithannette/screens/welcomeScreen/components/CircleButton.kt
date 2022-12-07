package com.sailinghawklabs.sweatwithannette.screens.welcomeScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.sweatwithannette.ui.theme.ExerciseTimeTheme

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonIcon: ImageVector? = null,
    buttonStyle: TextStyle = MaterialTheme.typography.displaySmall,
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
            width = 4.dp,
            color = borderColor
        )

    ) {
        if (buttonIcon != null) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                tint = textColor,
                imageVector = buttonIcon,
                contentDescription = "History"
            )
        } else {
            Text(
                text = buttonText,
                style = buttonStyle,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        }
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
@Preview
@Composable
fun CircleButtonPreviewImage() {
    ExerciseTimeTheme {
        CircleButton(
            buttonText = "Start",
            onClicked = {},
            buttonIcon = Icons.Default.DateRange
        )
    }
}