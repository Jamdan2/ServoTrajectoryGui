package com.company.servotrajectorygui.gui

import tornadofx.*

class ControlsView : View() {
    private val arduinoController by inject<ArduinoController>()

    override val root = hbox {
        addClass(Styles.spaced)
        button("run") {
            action {
                arduinoController.runTrajectory()
            }
        }
    }
}
