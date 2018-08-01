package com.company.servotrajectorygui

import org.ardulink.core.Link
import org.ardulink.core.Pin
import org.ardulink.core.convenience.Links

val link: Link = Links.getDefault()

val motorPin: Pin.AnalogPin = Pin.analogPin(MOTOR_PIN_NUM)
val enablePin: Pin.DigitalPin = Pin.digitalPin(ENABLE_PIN_NUM)
val sensorPin: Pin.AnalogPin = Pin.analogPin(0)
