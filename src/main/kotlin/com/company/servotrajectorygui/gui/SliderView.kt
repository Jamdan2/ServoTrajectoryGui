package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.*
import javafx.scene.Parent
import tornadofx.*
import kotlin.math.roundToInt

class SliderView : View() {
    override val root = vbox {
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
                    trajectory = Trajectory(trajectoryConfig)
                    trajectory.calculateDistancePoints()
                    trajectory.calculateVelocityPoints()
                    trajectory.calculateAccelerationPoints()
                    trajectory.calculateJerkPoints()
                    if (!trajectory.isValid) {
                        warning(
                                "trajectory is invalid",
                                trajectory.problems.joinToString(",\n")
                        )
                    }
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
                label("Distance: $INITIAL_DISTANCE") {
                    subscribe<DistanceSliderValueChanged> {
                        text = "Distance: ${it.newValue}"
                    }
                }
                slider(MIN_DISTANCE, MAX_DISTANCE, INITIAL_DISTANCE) {
                    minorTickCount = 15
                    majorTickUnit = 15.0
                    isShowTickMarks = true
                    isShowTickLabels = true
                    valueProperty().addListener { _, _, newValue ->
                        fire(DistanceSliderValueChanged(newValue.toInt()))
                        trajectoryConfig = TrajectoryConfig(
                                newValue.toInt(),
                                trajectoryConfig.maxVelocity,
                                trajectoryConfig.maxAcceleration,
                                trajectoryConfig.maxJerk
                        )
                    }
                }
            }
            vbox {
                label("Max Velocity: $INITIAL_VELOCITY") {
                    subscribe<VelocitySliderValueChanged> {
                        text = "Max Velocity: ${it.newValue}"
                    }
                }
                slider(MIN_VELOCITY, MAX_VELOCITY, INITIAL_VELOCITY) {
                    minorTickCount = 10
                    majorTickUnit = 0.1
                    isShowTickMarks = true
                    isShowTickLabels = true
                    valueProperty().addListener { _, _, newValue ->
                        fire(VelocitySliderValueChanged(newValue.toDouble()))
                        trajectoryConfig = TrajectoryConfig(
                                trajectoryConfig.distance,
                                newValue.toDouble(),
                                trajectoryConfig.maxAcceleration,
                                trajectoryConfig.maxJerk
                        )
                    }
                }
            }
            vbox {
                label("Max Acceleration: $INITIAL_ACCELERATION") {
                    subscribe<AccelerationSliderValueChanged> {
                        text = "Max Acceleration: ${it.newValue}"
                    }
                }
                slider(MIN_ACCELERATION, MAX_ACCELERATION, INITIAL_ACCELERATION) {
                    minorTickCount = 10
                    majorTickUnit = 0.001
                    isShowTickMarks = true
                    isShowTickLabels = true
                    valueProperty().addListener { _, _, newValue ->
                        fire(AccelerationSliderValueChanged(newValue.toDouble()))
                        trajectoryConfig = TrajectoryConfig(
                                trajectoryConfig.distance,
                                trajectoryConfig.maxVelocity,
                                newValue.toDouble(),
                                trajectoryConfig.maxJerk
                        )
                    }
                }
            }
            vbox {
                label("Max Jerk: $INITIAL_JERK") {
                    subscribe<JerkSliderValueChanged> {
                        text = "Max Jerk: ${it.newValue}"
                    }
                }
                slider(MIN_JERK, MAX_JERK, INITIAL_JERK) {
                    minorTickCount = 10
                    majorTickUnit = 0.001
                    isShowTickMarks = true
                    isShowTickLabels = true
                    valueProperty().addListener { _, _, newValue ->
                        fire(JerkSliderValueChanged(newValue.toDouble()))
                        trajectoryConfig = TrajectoryConfig(
                                trajectoryConfig.distance,
                                trajectoryConfig.maxVelocity,
                                trajectoryConfig.maxAcceleration,
                                newValue.toDouble()
                        )
                    }
                }
            }
        }
    }
}