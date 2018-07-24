package com.company.servotrajectorygui

import kotlin.math.*

fun setDistance(distance: Int) {
    trajectoryConfig = TrajectoryConfig(distance, trajectoryConfig.maxVelocity, trajectoryConfig.maxAcceleration, trajectoryConfig.maxJerk)
}

fun setVelocity(velocity: Double) {
    trajectoryConfig = TrajectoryConfig(trajectoryConfig.distance, velocity, trajectoryConfig.maxAcceleration, trajectoryConfig.maxJerk)
}

fun setAcceleration(acceleration: Double) {
    trajectoryConfig = TrajectoryConfig(trajectoryConfig.distance, trajectoryConfig.maxVelocity, acceleration, trajectoryConfig.maxJerk)
}

fun setJerk(jerk: Double) {
    trajectoryConfig = TrajectoryConfig(trajectoryConfig.distance, trajectoryConfig.maxVelocity, trajectoryConfig.maxAcceleration, jerk)
}

fun reinitializeTrajectory() {
    trajectory = Trajectory(trajectoryConfig)
}

var trajectoryConfig = TrajectoryConfig(90, 0.5, 0.005, 0.005)

var trajectory = Trajectory(trajectoryConfig)

data class TrajectoryConfig(
        val distance: Int,
        val maxVelocity: Double,
        val maxAcceleration: Double,
        val maxJerk: Double
)

class Trajectory(config: TrajectoryConfig) {
    var isValid = true
        private set

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
    private val t1 = (a / j).roundToLong()
    private val t2 = (v / a).roundToLong()
    private val t3 = t1 + t2
    private val t4 = (d / v).roundToLong()
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
    private fun j(t: Long) = when {
        t < 0 -> throw IllegalArgumentException("t cannot be negative")
        t <= t1 -> j1()
        t <= t2 -> j2()
        t <= t3 -> j3()
        t <= t4 -> j4()
        t <= t5 -> j5()
        t <= t6 -> j6()
        t <= t7 -> j7()
        else -> throw IllegalArgumentException("t cannot be greater than t7")
    }

    val jerkPoints = mutableListOf<Double>()

    fun calculateJerkPoints() {
        jerkPoints.clear()
        (0..t7).forEach { jerkPoints.add(j(it)) }
    }

    // acceleration functions
    private fun a1(t: Long) = j1() * t
    private fun a2() = a
    private fun a3(t: Long) = a2() + j3() * (t - t2)
    private fun a4() = 0.0
    private fun a5(t: Long) = j5() * (t - t4)
    private fun a6() = -a
    private fun a7(t: Long) = a6() + j7() * (t - t6)
    private fun a(t: Long) = when {
        t < 0 -> throw IllegalArgumentException("t cannot be negative")
        t <= t1 -> a1(t)
        t <= t2 -> a2()
        t <= t3 -> a3(t)
        t <= t4 -> a4()
        t <= t5 -> a5(t)
        t <= t6 -> a6()
        t <= t7 -> a7(t)
        else -> throw IllegalArgumentException("t cannot be greater than t7")
    }

    val accelerationPoints = mutableListOf<Double>()

    fun calculateAccelerationPoints() {
        accelerationPoints.clear()
        (0..t7).forEach { accelerationPoints.add(a(it)) }
    }

    // velocity functions
    private fun v1(t: Long) = 0.5 * j1() * t.toDouble().pow(2)
    private fun v2(t: Long) = v1(t1) + a1(t1) * (t - t1)
    private fun v3(t: Long) = v2(t2) + a2() * (t - t2) + 0.5 * j3() * (t - t2).toDouble().pow(2)
    private fun v4() = v
    private fun v5(t: Long) = v4() + a4() * (t - t4) + 0.5 * j5() * (t - t4).toDouble().pow(2)
    private fun v6(t: Long) = v5(t5) + a5(t5) * (t - t5)
    private fun v7(t: Long) = v6(t6) + a6() * (t - t6) + 0.5 * j7() * (t - t6).toDouble().pow(2)
    private fun v(t: Long) = when {
        t < 0 -> throw IllegalArgumentException("t cannot be negative")
        t <= t1 -> v1(t)
        t <= t2 -> v2(t)
        t <= t3 -> v3(t)
        t <= t4 -> v4()
        t <= t5 -> v5(t)
        t <= t6 -> v6(t)
        t <= t7 -> v7(t)
        else -> throw IllegalArgumentException("t cannot be greater than t7")
    }

    val velocityPoints = mutableListOf<Double>()

    fun calculateVelocityPoints() {
        velocityPoints.clear()
        (0..t7).forEach { velocityPoints.add(v(it)) }
    }

    // distance functions
    private fun d1(t: Long) = (1 / 6) * j1() * t.toDouble().pow(3)
    private fun d2(t: Long) = d1(t1) + v1(t1) * (t - t1) + 0.5 * a1(t1) * (t - t1).toDouble().pow(2)
    private fun d3(t: Long) = d2(t2) + v2(t2) * (t - t2) + 0.5 * a2() * (t - t2).toDouble().pow(2) + (1 / 6) * j3() * (t - t2).toDouble().pow(3)
    private fun d4(t: Long) = d3(t3) + v3(t3) * (t - t3)
    private fun d5(t: Long) = d4(t4) + v4() * (t - t4) + 0.5 * a4() * (t - t4).toDouble().pow(2) + (1 / 6) * j5() * (t - t4).toDouble().pow(3)
    private fun d6(t: Long) = d5(t5) + v5(t5) * (t - t5) + 0.5 * a5(t5) * (t - t5).toDouble().pow(2)
    private fun d7(t: Long) = d6(t6) + v6(t6) * (t - t6) + 0.5 * a6() * (t - t6).toDouble().pow(2) + (1 / 6) * j7() * (t - t6).toDouble().pow(3)
    private fun d(t: Long) = when {
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

    val distancePoints = mutableListOf<Double>()

    fun calculateDistancePoints() {
        distancePoints.clear()
        (0..t7).forEach { distancePoints.add(d(it)) }
    }
}
