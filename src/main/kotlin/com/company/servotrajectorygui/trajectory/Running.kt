package com.company.servotrajectorygui.trajectory

import com.company.servotrajectorygui.*
import kotlinx.coroutines.experimental.*

var trajectoryConfig = TrajectoryConfig(
        INITIAL_DISTANCE,
        INITIAL_VELOCITY,
        INITIAL_ACCELERATION,
        INITIAL_JERK
)

var trajectory = Trajectory(trajectoryConfig)

fun supplyMinimum() {
    link.switchAnalogPin(motorPin, 3)
}

fun Trajectory.run() {
    launch {
        link.switchDigitalPin(enablePin, true)
        val timer = timer(t7) {
            link.switchAnalogPin(motorPin, ((v(it) / 250) * 255 * 0.8 + 26).toInt())
        }
        timer.join()
        link.switchDigitalPin(enablePin, false)
        supplyMinimum()
    }
}
