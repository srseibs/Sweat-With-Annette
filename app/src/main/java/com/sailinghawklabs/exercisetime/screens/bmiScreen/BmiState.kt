package com.sailinghawklabs.exercisetime.screens.bmiScreen

import com.sailinghawklabs.exercisetime.util.BmiDiagnosis
import com.sailinghawklabs.exercisetime.util.NullBmiDiagnosis
import com.sailinghawklabs.exercisetime.util.NullBmiString

sealed class BmiUnits(val string: String) {
    object UsUnits : BmiUnits("US Units")
    object MetricUnits : BmiUnits("Metric Units")


    companion object {
        fun toList() =
            listOf(UsUnits, MetricUnits)

        fun toStringList() =
            toList().map{ it.string }
    }

}

data class BmiState(
    val units: List<BmiUnits> = BmiUnits.toList(),
    val selectedUnitIndex:Int = 0,
    var weightInKg: String = "",
    val weightInLbs: String = "",
    val heightInCm: String = "",
    val heightFeet: String = "",
    val heightInches: String = "",
    var bmiCalculated: String = NullBmiString,
    val bmiDiagnosis: BmiDiagnosis = NullBmiDiagnosis
)
