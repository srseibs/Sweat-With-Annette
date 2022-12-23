package com.sailinghawklabs.sweatwithannette.util

data class BmiDiagnosis(
    val bmiLessThan: Float,
    val label: String,
    val diagnosis: String
)

val NullBmiDiagnosis = BmiDiagnosis(-99f, "null", "null")

const val NullBmiString = "?"

const val RidiculouslyLargeBmi = 2000f

val BmiLevels = listOf<BmiDiagnosis>(
    BmiDiagnosis(
        bmiLessThan = 15f,
        label = "Very severely underweight",
        diagnosis = "You really need to take better care of yourself! Eat more!"
    ),
    BmiDiagnosis(
        bmiLessThan = 16f,
        label = "Severely underweight",
        diagnosis = "You really need to take better care of yourself! Eat more!"
    ),
    BmiDiagnosis(
        bmiLessThan = 18.5f,
        label = "Underweight",
        diagnosis = "You need to take better care of yourself! Eat more!"
    ),
    BmiDiagnosis(
        bmiLessThan = 25f,
        label = "Normal",
        diagnosis = "Congratulations! You are in good shape!"
    ),
    BmiDiagnosis(
        bmiLessThan = 30f,
        label = "Overweight",
        diagnosis = "You really need to take better care of yourself. Maybe workout or eat less?"
    ),
    BmiDiagnosis(
        bmiLessThan = 35f,
        label = "Moderately Obese",
        diagnosis = "You really need to take better care of yourself. Maybe workout or eat less?"
    ),
    BmiDiagnosis(
        bmiLessThan = 40f,
        label = "Severely Obese",
        diagnosis = "This is a serious condition! Act now to take better care of yourself. Maybe workout or eat less"
    ),
    BmiDiagnosis(
        bmiLessThan = 50f,
        label = "Very Severely Obese",
        diagnosis = "This is critical! Act now to take better care of yourself. Maybe workout or eat less"
    ),
    BmiDiagnosis(
        bmiLessThan = RidiculouslyLargeBmi+10f,
        label = "Possible non-human detected",
        diagnosis = "It is unclear how you are still living with a BMI this high. Contact researchers."
    )
)

fun diagnoseBmi(bmiValue: Float) =
    BmiLevels.first { bmiValue < it.bmiLessThan }
