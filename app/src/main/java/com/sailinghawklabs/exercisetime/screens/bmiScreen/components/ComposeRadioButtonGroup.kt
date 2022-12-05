package com.sailinghawklabs.exercisetime.screens.bmiScreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme


// inspiration from:
// https://github.com/vinaygaba/Learn-Jetpack-Compose-By-Example/blob/master/app/src/main/java/com/example/jetpackcompose/material/MaterialActivity.kt#L239
@Composable
fun ComposeRadioButtonGroup(
    modifier: Modifier = Modifier,

    groupOptions: List<String> = listOf("US", "Metric", "Roman"),
    selectedOption: Int = 0,
    onButtonPressed: (Int) -> Unit,

    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
) {

        Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        val isSelected = { index: Int ->
            index == selectedOption
        }
        val buttonColor = { enabled: Boolean ->
            if (enabled) {
                selectedColor
            } else {
                unselectedColor
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        ) {
            groupOptions.forEachIndexed { index, text ->

                val thisIsSelected = isSelected(index)
                val thisButtonColor = buttonColor(thisIsSelected)
                val animatedButtonColor: Color by animateColorAsState(
                    targetValue = thisButtonColor,
                    animationSpec = tween(800)
                )


                Column(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                ) {

                    Button(
                        onClick = { onButtonPressed(index) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = animatedButtonColor,
                        )
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = text,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }

}


@Preview
@Composable
fun ComposeRadioButtonGroupPreview() {
    ExerciseTimeTheme {
        ComposeRadioButtonGroup(
            onButtonPressed = {},
        )
    }
}