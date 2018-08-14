package com.company.servotrajectorygui.gui

import javafx.stage.Stage
import tornadofx.App

class Gui : App(MainView::class, Styles::class) {
    private val arduinoController by inject<ArduinoController>()

    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = true
        arduinoController.connect()
    }
}
