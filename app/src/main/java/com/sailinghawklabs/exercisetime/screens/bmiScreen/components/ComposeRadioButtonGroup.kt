package com.sailinghawklabs.exercisetime.screens.bmiScreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onSelectedChanged: (Int) -> Unit,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
) {

    var selected by remember { mutableStateOf(0) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp)
    ) {
        val updateSelection = { index: Int ->
            selected = index
            onSelectedChanged(selected)
        }
        val isSelected = { index: Int ->
            index == selected
        }
        val buttonColor = { enabled: Boolean ->
            if (enabled) {
                selectedColor
            } else {
                unselectedColor
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
        ) {
            groupOptions.forEachIndexed{ index, text  ->

                val thisIsSelected = isSelected(index)
                val thisButtonColor = buttonColor(thisIsSelected)
                val animatedButtonColor:Color by animateColorAsState(
                    targetValue = thisButtonColor,
                    animationSpec = tween(800)
                )


                Column(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                ) {

                    Button(
                        onClick = { updateSelection(index) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = animatedButtonColor,
                        )
                    ){
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
            onSelectedChanged = {}
        )
    }
}