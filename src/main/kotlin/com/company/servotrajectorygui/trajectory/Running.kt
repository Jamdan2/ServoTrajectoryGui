package com.company.servotrajectorygui.trajectory

import com.company.servotrajectorygui.*
import kotlinx.coroutines.experimental.*
import org.ardulink.core.Link
import org.ardulink.core.events.AnalogPinValueChangedEvent
import org.ardulink.core.events.DigitalPinValueChangedEvent
import org.ardulink.core.events.EventListener

var trajectoryConfig = TrajectoryConfig(
        INITIAL_DISTANCE,
        INITIAL_VELOCITY,
        INITIAL_ACCELERATION,
        INITIAL_JERK
)

var trajectory = Trajectory(trajectoryConfig)

var feedbackValue = 0

fun Link.supplyMinimum() {
    switchAnalogPin(motorPin, 3)
}

fun Link.listenToFeedback() {
    launch {
        startListening(sensorPin)
        addListener(object : EventListener {
            override fun stateChanged(event: AnalogPinValueChangedEvent?) {
                if (event != null) feedbackValue = (event.value / 818.4 * 250).toInt()
            }

            override fun stateChanged(event: DigitalPinValueChangedEvent?) = Unit
        })
    }
}

fun Link.run(trajectory: Trajectory) {
    launch {
        switchDigitalPin(enablePin, true)
        val timer = timer(trajectory.t7, 0.05) {
            link.switchAnalogPin(motorPin, ((trajectory.v(it) / 250) * 255 * 0.8 + 26).toInt())
        }
        timer.join()
        switchDigitalPin(enablePin, false)
        supplyMinimum()
    }
}
