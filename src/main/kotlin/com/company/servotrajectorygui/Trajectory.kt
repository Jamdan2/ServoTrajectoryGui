package com.company.servotrajectorygui

import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

var trajectory = Trajectory(0, 0.0, 0.0)

fun setDistance(d: Int) {
    trajectory = Trajectory(d, trajectory.maxVelocity, trajectory.maxAcceleration)
}

fun setVelocity(v: Double) {
    trajectory = Trajectory(trajectory.distance, v, trajectory.maxAcceleration)
}

fun setAcceleration(a: Double) {
    trajectory = Trajectory(trajectory.distance, trajectory.maxVelocity, a)
}

class Trajectory(val distance: Int, val maxVelocity: Double, val maxAcceleration: Double) {
    var isValid: Boolean = true

    private val d = distance
    private val v = min(maxVelocity, sqrt((distance * maxAcceleration).toFloat()).toDouble())
    private val a = maxAcceleration
    private val t1 = (v / a).toLong()
    private val t2 = (d / v).toLong()
    private val t3 = t1 + t2

    private fun distance1(ct: Long) = ((a * (ct.toFloat().pow(2))) / 2).toInt()

    private fun distance2(ct: Long) = ((v * (ct - t1)) + distance1(t1)).toInt()

    private fun distance3(ct: Long) = ((-(a * ((ct - t3).toFloat().pow(2))) / 2) + d).toInt()

    val distancePoints: List<Int> by lazy {
        (0..t3).map {
            when {
                it <= t1 -> distance1(it)
                it <= t2 -> distance2(it)
                it <= t3 -> distance3(it)
                else -> {
                    isValid = false
                    0
                }
            }
        }
    }
}
