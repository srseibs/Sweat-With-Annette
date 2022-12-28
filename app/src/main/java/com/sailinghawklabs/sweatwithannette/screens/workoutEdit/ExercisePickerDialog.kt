package com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sailinghawklabs.sweatwithannette.domain.model.Exercise
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList


@Composable
fun ExercisePickerDialog(
    title: String,
    exerciseList: List<Exercise>,
    modifier: Modifier = Modifier,
    onSelect: (List<Int>) -> Unit,
    onCancel: () -> Unit,
    selectLabel: String = "Select",
    cancelLabel: String = "Cancel",
) {

    val selections = remember { mutableStateListOf<Int>() }

    Dialog(
        onDismissRequest = { onCancel() },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Card(
                modifier = Modifier.padding(0.dp),

                ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {

                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(items = exerciseList,
                            key = { it.id }
                        ) { exercise ->


                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(80.dp)
                                    .padding(8.dp)
                            ) {
                                val isSelected = selections.contains(exercise.id)
                                IconButton(
                                    onClick = {
                                        if (isSelected)
                                            selections.remove(exercise.id)
                                        else
                                            selections.add(exercise.id)
                                    }
                                ) {

                                    Crossfade(
                                        targetState = isSelected,
                                    ) { isSelected ->
                                        if (isSelected) {
                                            Icon(
                                                modifier = Modifier.size(48.dp),
                                                imageVector = Icons.Default.CheckCircle,
                                                tint = MaterialTheme.colorScheme.primary,
                                                contentDescription = "Selected"
                                            )

                                        } else {
                                            Icon(
                                                modifier = Modifier.size(48.dp),
                                                imageVector = Icons.TwoTone.AddCircle,
                                                tint = MaterialTheme.colorScheme.onSurface,
                                                contentDescription = "Not Selected"
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = exercise.name,
                                    modifier = Modifier.weight(0.5f, true),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Image(
                                    modifier = Modifier.fillMaxWidth(0.4f),
                                    contentScale = ContentScale.Fit,
                                    painter = painterResource(id = exercise.imageResourceId),
                                    contentDescription = "Exercise image",
                                )
                            }
                            Divider(modifier = Modifier.fillMaxWidth())

                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Button(
                            onClick = { onCancel() })
                        {
                            Text(text = cancelLabel)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { onSelect(selections) })
                        {
                            Text(text = selectLabel)
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ExercisePickerDialogPreview() {

    SweatAnnetteTheme {
        ExercisePickerDialog(
            title = "Select Exercises to Add",
            exerciseList = DefaultExerciseList,
            onSelect = { },
            onCancel = { /*TODO*/ }
        )
    }

}

