package com.company.servotrajectorygui.gui

import tornadofx.*

class ControlsView : View() {
    private val trajectoryController by inject<TrajectoryController>()
    private val arduinoController by inject<ArduinoController>()

    override val root = hbox {
        addClass(Styles.spaced)
        button("calculate") {
            action {
                trajectoryController.calculateTrajectory()
            }
        }
        button("run") {
            action {
                arduinoController.runTrajectory()
            }
        }
    }
}
