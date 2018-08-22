package com.company.servotrajectorygui

data class Settings(
        val enablePinNum: Int,
        val motorPinNum: Int,
        val sensorPinNum: Int,
        val minInputVoltage: Double,
        val maxInputVoltage: Double
)
