package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.PidConfig
import tornadofx.Controller

class PidController : Controller() {
    var isPidUsed = false

    var pidConfig = PidConfig(1.0, 0.0, 0.0)

    fun configureKp(value: Double) {
        pidConfig = PidConfig(
                value,
                pidConfig.ki,
                pidConfig.kd
        )
    }

    fun configureKi(value: Double) {
        pidConfig = PidConfig(
                pidConfig.kp,
                value,
                pidConfig.kd
        )
    }

    fun configureKd(value: Double) {
        pidConfig = PidConfig(
                pidConfig.kp,
                pidConfig.ki,
                value
        )
    }
}
