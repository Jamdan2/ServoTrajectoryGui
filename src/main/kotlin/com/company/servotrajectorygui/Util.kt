package com.company.servotrajectorygui

import kotlin.math.pow
import kotlin.math.round

fun Double.root(n: Int): Double {
    if (n < 2) throw IllegalArgumentException("n must be more than 1")
    if (this <= 0.0) throw IllegalArgumentException("x must be positive")
    val np = n - 1
    fun iter(g: Double) = (np * g + this / g.pow(np.toDouble())) / n
    var g1 = this
    var g2 = iter(g1)
    while (g1 != g2) {
        g1 = iter(g1)
        g2 = iter(iter(g2))
    }
    return g1
}

fun virtualSecondsTimer(seconds: Double, block: (Double) -> Unit) {
    var t = 0.0
    while (t < seconds) {
        block(t)
        t += 0.001
    }
}

fun Double.roundTo(decimalPlaces: Int) =
        round(this * 10.0.pow(decimalPlaces)) / 10.0.pow(decimalPlaces)
