package com.company.servotrajectorygui

import javafx.event.EventHandler
import javafx.stage.Stage
import tornadofx.*
import kotlin.concurrent.thread

class Gui : App(MainView::class, Style::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
    }
}

var distanceSliderValue = 0
var velocitySliderValue = 0.0
var accelerationSliderValue = 0.0

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
                action {
                    thread {
                        trajectory.distancePoints.iterator().forEach {
                            link.setServoAngle(it)
                        }
                        Thread.sleep(1)
                    }
                }
            }
        }
        vbox {
            addClass(Style.wrapper)
            vbox {
                val distanceLabel = label("Distance: $distanceSliderValue")
                slider {
                    min = 0.0
                    max = 180.0
                    minorTickCount = 30
                    majorTickUnit = 15.0
                    isShowTickMarks = true
                    isShowTickLabels = true
                    valueProperty().addListener { _, _, newValue ->
                        distanceSliderValue = newValue.toInt()
                        distanceLabel.text = "Distance: $distanceSliderValue"
                    }
                    onMouseReleased = EventHandler {
                        setDistance(value.toInt())
                    }
                }
            }
            vbox {
                val velocityLabel = label("Max Velocity: $velocitySliderValue")
                slider {
                    min = 0.0
                    max = 1.0
                    minorTickCount = 100
                    isShowTickMarks = true
                    isShowTickLabels = true
                    valueProperty().addListener { _, _, newValue ->
                        velocitySliderValue = newValue.toDouble()
                        velocityLabel.text = "Max Velocity: $velocitySliderValue"
                    }
                    onMouseReleased = EventHandler {
                        setVelocity(value)
                    }
                }
            }
            vbox {
                val accelerationLabel = label("Max Acceleration: $accelerationSliderValue")
                slider {
                    min = 0.0
                    max = 0.1
                    minorTickCount = 100
                    isShowTickMarks = true
                    isShowTickLabels = true
                    valueProperty().addListener { _, _, newValue ->
                        setAcceleration(value)
                    }
                    valueProperty().addListener { _, _, newValue ->
                        accelerationSliderValue = newValue.toDouble()
                        accelerationLabel.text = "Max Acceleration: $accelerationSliderValue"
                    }
                    onMouseReleased = EventHandler {
                        setAcceleration(value)
                    }
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
