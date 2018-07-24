package com.company.servotrajectorygui

import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

data class TrajectoryConfig(
        val distance: Int,
        val maxVelocity: Double,
        val maxAcceleration: Double,
        val maxJerk: Double
)

class Trajectory(config: TrajectoryConfig) {
    var isValid = true

    init {
        if (
                config.distance < 0 ||
                config.maxVelocity < 0 ||
                config.maxAcceleration < 0 ||
                config.maxJerk < 0
        ) isValid = false
    }

    private val d = config.distance
    private val v = min(
            config.maxVelocity,
            if (config.maxAcceleration > (((config.maxJerk.pow(2)) * config.distance)/2).root(3)) {
                (((((config.maxJerk.pow(2)) * config.distance) / 2).root(3)).pow(2)) / config.maxJerk
            } else {
                (-(config.maxAcceleration.pow(2)) + sqrt((config.maxAcceleration.pow(4)) + (4 * (config.maxJerk.pow(2)) * config.distance * config.maxAcceleration))) / (2 * config.maxJerk)
            }
    )
    private val a = min(
            config.maxAcceleration,
            if (config.maxVelocity > (((((config.maxJerk.pow(2)) * config.distance) / 2).root(3)).pow(2)) / config.maxJerk) {
                (((config.maxJerk.pow(2)) * config.distance) / 2).root(3)
            } else {
                sqrt(config.maxVelocity * config.maxJerk)
            }
    )
    private val j = config.maxAcceleration

    // time constants
    private val t1 = a / j
    private val t2 = v / a
    private val t3 = t1 + t2
    private val t4 = d / v
    private val t5 = t1 + t4
    private val t6 = t2 + t4
    private val t7 = t3 + t4

    init {
        if (t2 - t1 < 0 || t4 - t2 - t1 < 0) isValid = false
    }

    // jerk functions
    private fun j1() = j
    private fun j2() = 0.0
    private fun j3() = -j
    private fun j4() = 0.0
    private fun j5() = -j
    private fun j6() = 0.0
    private fun j7() = j

    // acceleration functions
    private fun a1(t: Double) = j1() * t
    private fun a2() = a
    private fun a3(t: Double) = a2() + j3() * (t - t2)
    private fun a4() = 0.0
    private fun a5(t: Double) = j5() * (t - t4)
    private fun a6() = -a
    private fun a7(t: Double) = a6() + j7() * (t - t6)

    // velocity functions
    private fun v1(t: Double) = 0.5 * j1() * t.pow(2)
    private fun v2(t: Double) = v1(t1) + a1(t1) * (t - t1)
    private fun v3(t: Double) = v2(t2) + a2() * (t - t2) + 0.5 * j3() * (t - t2).pow(2)
    private fun v4() = v
    private fun v5(t: Double) = v4() + a4() * (t - t4) + 0.5 * j5() * (t - t4).pow(2)
    private fun v6(t: Double) = v5(t5) + a5(t5) * (t - t5)
    private fun v7(t: Double) = v6(t6) + a6() * (t - t6) + 0.5 * j7() * (t - t6).pow(2)

    // distance functions
    private fun d1(t: Double) = (1 / 6) * j1() * t.pow(3)
    private fun d2(t: Double) = d1(t1) + v1(t1) * (t - t1) + 0.5 * a1(t1) * (t - t1).pow(2)
    private fun d3(t: Double) = d2(t2) + v2(t2) * (t - t2) + 0.5 * a2() * (t - t2).pow(2) + (1 / 6) * j3() * (t - t2).pow(3)
    private fun d4(t: Double) = d3(t3) + v3(t3) * (t - t3)
    private fun d5(t: Double) = d4(t4) + v4() * (t - t4) + 0.5 * a4() * (t - t4).pow(2) + (1 / 6) * j5() * (t - t4).pow(3)
    private fun d6(t: Double) = d5(t5) + v5(t5) * (t - t5) + 0.5 * a5(t5) * (t - t5).pow(2)
    private fun d7(t: Double) = d6(t6) + v6(t6) * (t - t6) + 0.5 * a6() * (t - t6).pow(2) + (1 / 6) * j7() * (t - t6).pow(3)

    // final distance function
    private fun d(t: Double) = when {
        t < 0 -> throw IllegalArgumentException("t cannot be negative")
        t <= t1 -> d1(t)
        t <= t2 -> d2(t)
        t <= t3 -> d3(t)
        t <= t4 -> d4(t)
        t <= t5 -> d5(t)
        t <= t6 -> d6(t)
        t <= t7 -> d7(t)
        else -> throw IllegalArgumentException("t cannot be greater than t7")
    }
}
