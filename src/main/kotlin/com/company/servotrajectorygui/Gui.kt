package com.company.servotrajectorygui

import tornadofx.*

class Gui : App(MainView::class)

class MainView : View() {
    override val root = vbox {
        padding = insets(10)
        hbox {
            button {
                text = "Reset servo"
            }
            button {
                text = "Run trajectory"
            }
        }
        vbox {
            label("Distance")
            slider {
                min = 0.0
                max = 180.0
                minorTickCount = 30
                majorTickUnit = 15.0
                isShowTickMarks = true
                isShowTickLabels = true
            }
            label("Max Velocity")
            slider {
                min = 0.0
                max = 180.0
                minorTickCount = 30
                majorTickUnit = 15.0
                isShowTickMarks = true
                isShowTickLabels = true
            }
            label("Max Acceleration")
            slider {
                min = 0.0
                max = 180.0
                minorTickCount = 30
                majorTickUnit = 15.0
                isShowTickMarks = true
                isShowTickLabels = true
            }
        }
    }
}
