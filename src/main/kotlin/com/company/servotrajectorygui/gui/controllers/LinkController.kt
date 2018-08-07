package com.company.servotrajectorygui.gui.controllers

import com.company.servotrajectorygui.ENABLE_PIN_NUM
import com.company.servotrajectorygui.MOTOR_PIN_NUM
import com.company.servotrajectorygui.SENSOR_PIN_NUM
import com.company.servotrajectorygui.timer
import com.company.servotrajectorygui.trajectory.Trajectory
import com.company.servotrajectorygui.trajectory.v
import kotlinx.coroutines.experimental.launch
import org.ardulink.core.Link
import org.ardulink.core.Pin
import org.ardulink.core.convenience.Links
import org.ardulink.core.events.AnalogPinValueChangedEvent
import org.ardulink.core.events.DigitalPinValueChangedEvent
import org.ardulink.core.events.EventListener
import tornadofx.Controller
import tornadofx.error

class LinkController : Controller() {
    private lateinit var link: Link
    var linked = false

    private val motorPin: Pin.AnalogPin = Pin.analogPin(MOTOR_PIN_NUM)
    private val enablePin: Pin.DigitalPin = Pin.digitalPin(ENABLE_PIN_NUM)
    private val sensorPin: Pin.AnalogPin = Pin.analogPin(SENSOR_PIN_NUM)

    private var feedbackValue = 0

    fun connect() {
        if (!linked) try {
            link = Links.getDefault()
            linked = true
            supplyMinimumVoltage()
            listenToFeedback()
        } catch (e: RuntimeException) {
            error("Failed to connect to Arduino", "Is your arduino plugged in?")
        }
    }

    fun supplyMinimumVoltage() {
        if (linked) link.switchAnalogPin(motorPin, 3)
    }

    fun listenToFeedback() {
        if (linked) launch {
            link.startListening(sensorPin)
            link.addListener(object : EventListener {
                override fun stateChanged(event: AnalogPinValueChangedEvent?) {
                    if (event != null) feedbackValue = (event.value / 818.4 * 250).toInt()
                }

                override fun stateChanged(event: DigitalPinValueChangedEvent?) = Unit
            })
        }
    }

    fun runTrajectory(trajectory: Trajectory) {
        connect()
        if (linked) launch {
            link.switchDigitalPin(enablePin, true)
            val timer = timer(trajectory.t7, 0.05) {
                link.switchAnalogPin(motorPin, ((trajectory.v(it) / 250) * 255 * 0.8 + 26).toInt())
            }
            timer.join()
            link.switchDigitalPin(enablePin, false)
            supplyMinimumVoltage()
        }
    }
}
