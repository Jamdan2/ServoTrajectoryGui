package com.company.servotrajectorygui

import kotlinx.coroutines.experimental.delay
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

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

fun virtualTimer(seconds: Double, tick: Double = 0.001, block: (Double) -> Unit) {
    var now = 0.0
    while (now < seconds) {
        block(now)
        now += tick
    }
}

suspend fun timer(seconds: Double, tick: Double = 0.001, block: (Double) -> Unit) {
    var now = 0.0
    val tickMilliseconds = (tick * 1000).roundToInt()
    while (now < seconds) {
        block(now)
        now += tick
        delay(tickMilliseconds)
    }
}

fun Double.roundTo(decimalPlaces: Int) =
        round(this * 10.0.pow(decimalPlaces)) / 10.0.pow(decimalPlaces)
