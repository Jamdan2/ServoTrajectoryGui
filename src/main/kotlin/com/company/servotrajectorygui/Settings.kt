package com.company.servotrajectorygui

data class Settings(
        val enablePinNum: Int,
        val motorPinNum: Int,
        val sensorPinNum: Int,
        val minOutputPercentage: Double,
        val maxOutputPercentage: Double
)
