package com.sailinghawklabs.exercisetime.screens.bmiScreen

sealed class BmiEventsToViewModel {
    object MetricUnitsPressed : BmiEventsToViewModel()
    object UsUnitsPressed : BmiEventsToViewModel()

    class UnitIndexPressed(val index: Int) : BmiEventsToViewModel()

    class WeightLbChanged(val newEntry: String): BmiEventsToViewModel()
    class WeightKgChanged(val newEntry: String): BmiEventsToViewModel()
    class HeightFtChanged(val newEntry: String): BmiEventsToViewModel()
    class HeightInchesChanged(val newEntry: String): BmiEventsToViewModel()
    class HeightCmChanged(val newEntry: String): BmiEventsToViewModel()

    object onCalculatePressed: BmiEventsToViewModel()
}
