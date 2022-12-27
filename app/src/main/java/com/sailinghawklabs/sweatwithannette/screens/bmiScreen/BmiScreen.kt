package com.sailinghawklabs.sweatwithannette.screens.bmiScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.sweatwithannette.screens.bmiScreen.components.ComposeRadioButtonGroup
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme
import com.sailinghawklabs.sweatwithannette.util.BmiDiagnosis
import com.sailinghawklabs.sweatwithannette.util.BmiLevels
import com.sailinghawklabs.sweatwithannette.util.NullBmiDiagnosis
import com.sailinghawklabs.sweatwithannette.util.NullBmiString
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BmiViewModel = hiltViewModel(),
) {

    Log.d("BmiScreen", "BmiScreen: ${viewModel.bmiState.selectedUnitIndex}")

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            snackbarHostState.showSnackbar(
                message = viewModel.errorMessage,
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short,
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back arrow"
                        )
                    }
                }
            )
        },

        ) { scaffoldPadding ->
        BmiScreenContent(
            modifier = modifier.padding(scaffoldPadding),
            onEvent = { viewModel.onEvent(it) },
            unitsTitleList = BmiUnits.toStringList(),
            selectedUnitIndex = viewModel.bmiState.selectedUnitIndex,
            weightInKgs = viewModel.bmiState.weightInKg,
            weightInLbs = viewModel.bmiState.weightInLbs,
            heightInCm = viewModel.bmiState.heightInCm,
            heightFeet = viewModel.bmiState.heightFeet,
            heightInches = viewModel.bmiState.heightInches,
            bmiValue = viewModel.bmiState.bmiCalculated,
            bmiDiagnosis = viewModel.bmiState.bmiDiagnosis,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BmiScreenContent(
    modifier: Modifier = Modifier,
    weightInKgs: String,
    weightInLbs: String,
    heightInCm: String,
    onEvent: (BmiEventsToViewModel) -> Unit,
    selectedUnitIndex: Int,
    unitsTitleList: List<String>,
    heightInches: String,
    heightFeet: String,
    bmiValue:String,
    bmiDiagnosis: BmiDiagnosis,
    ) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
            .clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ComposeRadioButtonGroup(
            groupOptions = unitsTitleList,
            selectedColor = MaterialTheme.colorScheme.secondary,

            onButtonPressed = {
                onEvent(BmiEventsToViewModel.UnitIndexPressed(it))
            },
            selectedOption = selectedUnitIndex
        )
        Spacer(modifier = Modifier.height(16.dp))


        if (BmiUnits.toList()[selectedUnitIndex] == BmiUnits.MetricUnits) {

            OutlinedTextField(
                value = weightInKgs,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("WEIGHT (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { onEvent(BmiEventsToViewModel.WeightKgChanged(it)) }
            )
        } else if (BmiUnits.toList()[selectedUnitIndex] == BmiUnits.UsUnits) {
            OutlinedTextField(
                value = weightInLbs,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("WEIGHT (lbs)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { onEvent(BmiEventsToViewModel.WeightLbChanged(it)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        if (BmiUnits.toList()[selectedUnitIndex] == BmiUnits.MetricUnits) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = heightInCm,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("HEIGHT (cm)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    onValueChange = { onEvent(BmiEventsToViewModel.HeightCmChanged(it)) }
                )
            }
        } else if (BmiUnits.toList()[selectedUnitIndex] == BmiUnits.UsUnits) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = heightFeet,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    label = { Text("HEIGHT (Feet)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { onEvent(BmiEventsToViewModel.HeightFtChanged(it)) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = heightInches,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    label = { Text("HEIGHT (Inches)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    onValueChange = { onEvent(BmiEventsToViewModel.HeightInchesChanged(it)) }
                )
            }
        } else {
            throw Exception("Unexpected value for selected Units")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(BmiEventsToViewModel.onCalculatePressed)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your BMI",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = bmiValue,
            style = MaterialTheme.typography.displayMedium
        )
        if (bmiDiagnosis != NullBmiDiagnosis && bmiValue != NullBmiString) {
            Text(
                text = bmiDiagnosis.label,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            Text(
                text = bmiDiagnosis.diagnosis,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun BmiScreenPreview() {

    SweatAnnetteTheme {
        BmiScreenContent(
            weightInKgs = "84.2",
            weightInLbs = "124.2",
            heightInCm = "66.1",
            onEvent = {},
            selectedUnitIndex = 0,
            unitsTitleList = BmiUnits.toStringList(),
            heightFeet = "5",
            heightInches = "2.5",
            bmiValue = "31.2",
            bmiDiagnosis = BmiLevels[5],
        )

    }
}