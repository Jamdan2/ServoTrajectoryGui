package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.*
import org.ardulink.core.Pin
import tornadofx.Controller

class SettingsController : Controller() {
    private val arduinoController by inject<ArduinoController>()

    private var settings = Settings(
            ENABLE_PIN_NUM,
            MOTOR_PIN_NUM,
            SENSOR_PIN_NUM,
            MIN_INPUT_VOLTAGE,
            MAX_INPUT_VOLTAGE,
            MIN_OUTPUT_VOLTAGE,
            MAX_OUTPUT_VOLTAGE
    )

    fun copySettings() = settings.copy()

    fun applySettings(settings: Settings) {
        this.settings = settings
        arduinoController.apply {
            enablePin = Pin.digitalPin(settings.enablePinNum)
            motorPin = Pin.analogPin(settings.motorPinNum)
            sensorPin = Pin.analogPin(settings.sensorPinNum)
        }
        minInputVoltage = settings.minInputVoltage
        maxInputVoltage = settings.maxInputVoltage
        minOutputVoltage = settings.minOutputVoltage
        maxOutputVoltage = settings.maxOutputVoltage
    }
}
