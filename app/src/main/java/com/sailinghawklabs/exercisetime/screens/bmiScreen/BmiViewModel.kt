package com.sailinghawklabs.exercisetime.screens.bmiScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.exercisetime.util.diagnoseBmi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BmiViewModel @Inject constructor() : ViewModel() {

    var bmiState by mutableStateOf(BmiState())
        private set

    var errorMessage by mutableStateOf("")
        private set

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()


    fun onEvent(event: BmiEventsToViewModel) {
        bmiState = bmiState.copy(
            bmiCalculated = BmiState().bmiCalculated
        )

        when (event) {
            is BmiEventsToViewModel.HeightFtChanged ->
                bmiState = bmiState.copy(heightFeet = event.newEntry)

            is BmiEventsToViewModel.HeightInchesChanged ->
                bmiState = bmiState.copy(heightInches = event.newEntry)

            is BmiEventsToViewModel.HeightCmChanged ->
                bmiState = bmiState.copy(heightInCm = event.newEntry)

            is BmiEventsToViewModel.UnitIndexPressed ->
                bmiState = bmiState.copy(selectedUnitIndex = event.index)

            BmiEventsToViewModel.MetricUnitsPressed -> TODO()
            BmiEventsToViewModel.UsUnitsPressed -> TODO()

            is BmiEventsToViewModel.WeightKgChanged ->
                bmiState = bmiState.copy(weightInKg = event.newEntry)

            is BmiEventsToViewModel.WeightLbChanged ->
                bmiState = bmiState.copy(weightInLbs = event.newEntry)

            BmiEventsToViewModel.onCalculatePressed ->
                calculate()
        }
    }


    // BMI = weightKg / (height_m)^2
    //   https://www.cdc.gov/nccdphp/dnpao/growthcharts/training/bmiage/page5_1.html
    private var heightInMeters = 0f
    private var weightInKilograms = 0f


    private fun calculate() {
        bmiState = bmiState.copy(
            bmiCalculated = BmiState().bmiCalculated
        )
        if (validateInputs()) {
            val bmi = weightInKilograms / (heightInMeters * heightInMeters)
            bmiState = bmiState.copy(
                bmiCalculated = "%.1f".format(bmi),
                bmiDiagnosis = diagnoseBmi(bmi),
            )
        } else {
            viewModelScope.launch{ _isMessageShown.emit(true) }
        }
    }

    // multiplication factors for conversion
    private val centimetersToMeters = 0.01f
    private val poundsToKilograms = 0.4535923f
    private val inchesToMeters = 0.02539999f

    private fun validateInputs(): Boolean {
        errorMessage = ""
        when (BmiUnits.toList()[bmiState.selectedUnitIndex]) {
            BmiUnits.MetricUnits -> {

                tryConvertStringToFloat("Weight field (kg)", bmiState.weightInKg).let {
                    if (it.first.isNotEmpty()) {
                        errorMessage = it.first
                        return false
                    } else {
                        weightInKilograms = it.second
                    }
                }

                tryConvertStringToFloat("Height field (cm)", bmiState.heightInCm).let {
                    if (it.first.isNotEmpty()) {
                        errorMessage = it.first
                        return false
                    } else {
                        heightInMeters = it.second * centimetersToMeters
                    }
                }
            }

            BmiUnits.UsUnits -> {
                if (bmiState.weightInLbs.isBlank()) {
                    errorMessage = "Weight field (lbs) cannot be empty"
                }
                if (bmiState.heightFeet.isBlank()) {
                    errorMessage = "Height field (feet) cannot be empty"
                }

                tryConvertStringToFloat("Weight field (lb)", bmiState.weightInLbs).let {
                    if (it.first.isNotEmpty()) {
                        errorMessage = it.first
                        return false
                    } else {
                        weightInKilograms = it.second * poundsToKilograms
                    }
                }

                var feet: Float
                tryConvertStringToFloat("Height field (ft)", bmiState.heightFeet).let {
                    if (it.first.isNotEmpty()) {
                        errorMessage = it.first
                        return false
                    } else {
                        feet = it.second
                    }
                }

                var inches = 0f
                if (bmiState.heightInches.isNotBlank()) {
                    tryConvertStringToFloat("Height field (inches)", bmiState.heightInches).let {
                        if (it.first.isNotEmpty()) {
                            errorMessage = it.first
                            return false
                        } else {
                            inches = it.second
                        }
                    }
                }


                // convert feet + inches into meters
                val totalInches = (feet * 12.0f) + inches
                heightInMeters = totalInches * inchesToMeters
                Log.d("BmiViewModel", "validateInputs: heightInMeters = $heightInMeters")

            }

        }
        return true
    }

    private fun tryConvertStringToFloat(
        label: String,
        valueString: String,
    ): Pair<String, Float> {

        Log.d("BmiViewModel", "tryConvertStringToFloat: label = $label, value = '$valueString', isEmpty:${valueString.isEmpty()}")

        if (valueString.isEmpty())
            return Pair("$label: value cannot be empty", 0f)

        try {
            val convertedFloat = valueString.toFloat()

            if (convertedFloat < 0f) {
                return Pair("$label: value cannot be negative", 0f)
            } else {
                return Pair("", convertedFloat)
            }

        } catch (e: Exception) {
            Log.d("BmiViewModel", "tryConvertStringToFloat: exception: ${e.toString()}")
            return Pair("$label: number format error", 0f)
        }
    }
}