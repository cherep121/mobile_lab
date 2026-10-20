package com.example.tipcalculator_lab5

import androidx.annotation.VisibleForTesting
import java.text.NumberFormat
import kotlin.math.ceil

@VisibleForTesting
internal fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0,
    roundUp: Boolean = false,
): String {
    var tip = amount * tipPercent / 100
    if (roundUp) tip = ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}
