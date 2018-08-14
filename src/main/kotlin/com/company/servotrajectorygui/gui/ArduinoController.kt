package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.*
import com.company.servotrajectorygui.trajectory.v
import kotlinx.coroutines.experimental.launch
import org.ardulink.core.Link
import org.ardulink.core.Pin
import org.ardulink.core.convenience.Links
import org.ardulink.core.events.AnalogPinValueChangedEvent
import org.ardulink.core.events.DigitalPinValueChangedEvent
import org.ardulink.core.events.EventListener
import tornadofx.Controller

class ArduinoController : Controller() {
    private val trajectoryController by inject<TrajectoryController>()
    private val pidController by inject<PidController>()

    private var link: Link? = null

    private val motorPin: Pin.AnalogPin = Pin.analogPin(9)
    private val enablePin: Pin.DigitalPin = Pin.digitalPin(10)
    private val sensorPin: Pin.AnalogPin = Pin.analogPin(0)

    var feedback = 0.0

    fun connect() {
        try {
            if (link == null) link = Links.getDefault()
            supplyMinimumVoltage()
            listenToFeedback()
        } catch (e: RuntimeException) {
            tornadofx.error(
                    "Failed to connect to Arduino.",
                    "Is your Arduino plugged in?"
            )
        }
    }

    private fun supplyMinimumVoltage() {
         link?.switchAnalogPin(motorPin, 3)
    }

    private fun listenToFeedback() {
        if (link != null) {
            link!!.startListening(sensorPin)
            link!!.addListener(object : EventListener {
                override fun stateChanged(event: AnalogPinValueChangedEvent?) {
                    if (event != null) feedback = outputVoltageToRps(event.value)
                }

                override fun stateChanged(event: DigitalPinValueChangedEvent?) = Unit
            })
        }
    }

    fun runTrajectory() {
        connect()
        if (link != null) {
            launch {
                link?.switchDigitalPin(enablePin, true)
                if (pidController.isPidUsed) {
                    val pid = Pid(pidController.pidConfig, 0.05)
                    val timer = timer(trajectoryController.trajectory.t7, 0.05) {
                        link?.switchAnalogPin(motorPin, rpsToInputVoltage(pid.next(trajectoryController.trajectory.v(it), feedback)))
                    }
                    timer.join()
                } else {
                    val timer = timer(trajectoryController.trajectory.t7, 0.05) {
                        link?.switchAnalogPin(motorPin, rpsToInputVoltage(trajectoryController.trajectory.v(it)))
                    }
                    timer.join()
                }
                link?.switchDigitalPin(enablePin, false)
                supplyMinimumVoltage()
            }
        }
    }
}