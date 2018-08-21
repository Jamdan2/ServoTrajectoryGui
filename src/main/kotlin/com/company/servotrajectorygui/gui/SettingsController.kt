package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.ENABLE_PIN_NUM
import com.company.servotrajectorygui.MOTOR_PIN_NUM
import com.company.servotrajectorygui.SENSOR_PIN_NUM
import com.company.servotrajectorygui.Settings
import org.ardulink.core.Pin
import tornadofx.Controller

class SettingsController : Controller() {
    private val arduinoController by inject<ArduinoController>()

    private var settings = Settings(
            ENABLE_PIN_NUM,
            MOTOR_PIN_NUM,
            SENSOR_PIN_NUM
    )

    fun copySettings() = settings.copy()

    fun applySettings(settings: Settings) {
        this.settings = settings
        arduinoController.apply {
            enablePin = Pin.digitalPin(settings.enablePinNum)
            motorPin = Pin.analogPin(settings.motorPinNum)
            sensorPin = Pin.analogPin(settings.sensorPinNum)
        }
    }
}
