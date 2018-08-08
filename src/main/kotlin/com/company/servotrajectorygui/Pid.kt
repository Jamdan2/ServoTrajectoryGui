package com.company.servotrajectorygui

data class PidConfig(val kp: Double, val ki: Double, val kd: Double)

class Pid(config: PidConfig, val dt: Double) {
    private val kp = config.kp
    private val ki = config.ki
    private val kd = config.kd

    private var integral = 0.0
    private var lastError = 0.0

    fun next(setPoint: Double, actual: Double): Double {
        val error = setPoint - actual
        integral += error * dt
        val derivative = (error - lastError) / dt
        lastError = error
        return (kp * error) + (ki * integral) + (kd * derivative)
    }
}
