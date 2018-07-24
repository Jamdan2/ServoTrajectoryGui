package com.company.servotrajectorygui

import com.sun.org.apache.bcel.internal.Repository.addClass
import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.chart.Axis
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Button
import javafx.stage.Stage
import tornadofx.*
import java.awt.Color
import kotlin.math.roundToInt

object TrajectoryRecalculated : FXEvent(EventBus.RunOn.ApplicationThread)

class Gui : App(MainView::class, Style::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
    }
}

class MainView : View() {
    override val root = vbox {
        hbox {
            title = "Servo Trajectory Gui"
            addClass(Style.wrapper)
            vbox {
                hbox {
                    addClass(Style.wrapper)
                    button {
                        text = "Reset servo"
                        action {
                            link.setServoAngle(0)
                        }
                    }
                    button {
                        text = "Calculate trajectory"
                        action {
                            reinitializeTrajectory()
                            trajectory.calculateDistancePoints()
                            trajectory.calculateVelocityPoints()
                            trajectory.calculateAccelerationPoints()
                            trajectory.calculateJerkPoints()
                            if (!trajectory.isValid) warning("trajectory is invalid")
                            fire(TrajectoryRecalculated)
                        }
                    }
                    button {
                        text = "Run trajectory"
                        action {
                            if (trajectory.isValid) trajectory.distancePoints.iterator().forEach {
                                link.setServoAngle(it.roundToInt())
                                Thread.sleep(1)
                            } else error("trajectory is invalid")
                        }
                    }
                }
                vbox {
                    addClass(Style.wrapper)
                    vbox {
                        val distanceLabel = label("Distance: 90")
                        slider(0, 180, 90) {
                            minorTickCount = 15
                            majorTickUnit = 15.0
                            isShowTickMarks = true
                            isShowTickLabels = true
                            valueProperty().addListener { _, _, newValue ->
                                distanceLabel.text = "Distance: ${newValue.toInt()}"
                            }
                            onMouseReleased = EventHandler {
                                setDistance(value.toInt())
                            }
                        }
                    }
                    vbox {
                        val velocityLabel = label("Max Velocity: 0.5")
                        slider(0, 1, 0.5) {
                            minorTickCount = 10
                            majorTickUnit = 0.1
                            isShowTickMarks = true
                            isShowTickLabels = true
                            valueProperty().addListener { _, _, newValue ->
                                velocityLabel.text = "Max Velocity: ${newValue.toDouble()}"
                            }
                            onMouseReleased = EventHandler {
                                setVelocity(value)
                            }
                        }
                    }
                    vbox {
                        val accelerationLabel = label("Max Acceleration: 0.005")
                        slider(0, 0.01, 0.005) {
                            minorTickCount = 10
                            majorTickUnit = 0.001
                            isShowTickMarks = true
                            isShowTickLabels = true
                            valueProperty().addListener { _, _, newValue ->
                                accelerationLabel.text = "Max Acceleration: ${newValue.toDouble()}"
                            }
                            onMouseReleased = EventHandler {
                                setAcceleration(value)
                            }
                        }
                    }
                    vbox {
                        val jerkLabel = label("Max Jerk: 0.005")
                        slider(0, 0.01, 0.005) {
                            minorTickCount = 10
                            majorTickUnit = 0.1
                            isShowTickMarks = true
                            isShowTickLabels = true
                            valueProperty().addListener { _, _, newValue ->
                                jerkLabel.text = "Max Jerk: ${newValue.toDouble()}"
                            }
                            onMouseReleased = EventHandler {
                                setJerk(value)
                            }
                        }
                    }
                }
            }
            vbox {
                hbox {
                    scatterchart("Distance", NumberAxis(), NumberAxis()) {
                        setPrefSize(250.0, 250.0)
                        series("Distance") {
                            trajectory.calculateDistancePoints()
                            trajectory.distancePoints.forEachIndexed { index, value ->
                                data(index, value)
                            }
                            subscribe<TrajectoryRecalculated> {
                                data.clear()
                                trajectory.distancePoints.forEachIndexed { index, value ->
                                    data(index, value)
                                }
                            }
                        }
                    }
                    scatterchart("Velocity", NumberAxis(), NumberAxis()) {
                        setPrefSize(250.0, 250.0)
                        series("Velocity") {
                            trajectory.calculateVelocityPoints()
                            trajectory.velocityPoints.forEachIndexed { index, value ->
                                data(index, value)
                            }
                            subscribe<TrajectoryRecalculated> {
                                data.clear()
                                trajectory.velocityPoints.forEachIndexed { index, value ->
                                    data(index, value)
                                }
                            }

                        }
                    }
                }
                hbox {
                    scatterchart("Acceleration", NumberAxis(), NumberAxis()) {
                        setPrefSize(250.0, 250.0)
                        series("Acceleration") {
                            trajectory.calculateAccelerationPoints()
                            trajectory.accelerationPoints.forEachIndexed { index, value ->
                                data(index, value)
                            }
                            subscribe<TrajectoryRecalculated> {
                                data.clear()
                                trajectory.accelerationPoints.forEachIndexed { index, value ->
                                    data(index, value)
                                }
                            }
                        }
                    }
                    scatterchart("Jerk", NumberAxis(), NumberAxis()) {
                        setPrefSize(250.0, 250.0)
                        series("Jerk") {
                            trajectory.calculateJerkPoints()
                            trajectory.jerkPoints.forEachIndexed { index, value ->
                                data(index, value)
                            }
                            subscribe<TrajectoryRecalculated> {
                                data.clear()
                                trajectory.jerkPoints.forEachIndexed { index, value ->
                                    data(index, value)
                                }
                            }
                        }
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
