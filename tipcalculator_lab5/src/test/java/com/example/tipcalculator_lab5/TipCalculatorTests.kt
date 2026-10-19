package com.example.tipcalculator_lab5

import java.text.NumberFormat
import org.junit.Assert.assertEquals
import org.junit.Test

class TipCalculatorTests {
    @Test
    fun calculateTip_20PercentNoRoundup() {
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(amount = 10.0, tipPercent = 20.0, roundUp = false)

        assertEquals(expectedTip, actualTip)
    }
}
