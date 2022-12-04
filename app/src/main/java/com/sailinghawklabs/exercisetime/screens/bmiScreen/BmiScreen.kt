package com.sailinghawklabs.exercisetime.screens.bmiScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.screens.bmiScreen.components.ComposeRadioButtonGroup
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = "Calculate BMI",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack  ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back arrow"
                        )
                    }
                }
            )
        },

        ) { scaffoldPadding ->

        Column(
            modifier = modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            ComposeRadioButtonGroup(
                groupOptions = listOf("METRIC UNITS", "US UNITS"),
                onSelectedChanged = { }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "WEIGHT (kg)",
                onValueChange = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "HEIGHT (cm)",
                    onValueChange = {}
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = "HEIGHT (Feet)",
                    onValueChange = {}
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = "HEIGHT (Inches)",
                    onValueChange = {}
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                 modifier = Modifier.fillMaxWidth(),
            ){
                Text("Calculate")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "BMI",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "32.1",
                style = MaterialTheme.typography.displayMedium
            )


        }
    }
}




@Preview(showSystemUi = true)
@Composable
fun BmiScreenPreview() {

    ExerciseTimeTheme {
        BmiScreen(
            goBack = {}
        )

    }
}