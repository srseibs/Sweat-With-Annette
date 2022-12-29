package com.sailinghawklabs.sweatwithannette.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BmiLevelsKtTest {

    @Test
    fun `Zero BMI returns first level`() {
        val result = diagnoseBmi(0f)
        assertThat(result).isEqualTo(BmiLevels[0])
    }

    @Test
    fun `Large BMI returns last level`() {
        val result = diagnoseBmi(RidiculouslyLargeBmi)
        assertThat(result).isEqualTo(BmiLevels.last())
    }

    @Test
    fun `Negative BMI returns first level`() {
        val result = diagnoseBmi(-10f)
        assertThat(result).isEqualTo(BmiLevels[0])
    }

    @Test
    fun `Overflow BMI returns last level`() {
        val result = diagnoseBmi(100f * RidiculouslyLargeBmi)
        assertThat(result).isEqualTo(BmiLevels.last())
    }
}