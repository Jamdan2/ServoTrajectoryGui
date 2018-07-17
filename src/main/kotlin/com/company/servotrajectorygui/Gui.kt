package com.company.servotrajectorygui

import javafx.stage.Stage
import tornadofx.*

class Gui : App(MainView::class, Style::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
    }
}

class MainView : View() {
    override val root = vbox {
        title = "Servo Trajectory Gui"
        addClass(Style.wrapper)
        hbox {
            addClass(Style.wrapper)
            button {
                text = "Reset servo"
                action {
                    link.setServoAngle(0)
                }
            }
            button {
                text = "Run trajectory"
            }
        }
        vbox {
            addClass(Style.wrapper)
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
            }
            vbox {
                label("Max Velocity")
                slider {
                    min = 0.0
                    max = 180.0
                    minorTickCount = 30
                    majorTickUnit = 15.0
                    isShowTickMarks = true
                    isShowTickLabels = true
                }
            }
            vbox {
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
}

class Style : Stylesheet() {
    companion object {
        val wrapper by cssclass()
    }
    init {
        root {
            padding = box(5.mm)
        }
        slider {
            prefWidth = 150.mm
        }
        wrapper {
            spacing = 3.mm
        }
    }
}
