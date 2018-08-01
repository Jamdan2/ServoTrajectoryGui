package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.*
import com.company.servotrajectorygui.trajectory.*
import com.sun.org.apache.bcel.internal.Repository.addClass
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import tornadofx.*
import tornadofx.Stylesheet.Companion.label
import tornadofx.Stylesheet.Companion.slider
import kotlin.concurrent.thread
import kotlin.math.roundToInt

class SliderView : View() {
    override val root = vbox {
        hbox {
            addClass(Style.wrapper)
            button {
                text = "Calculate trajectory"
                action {
                        trajectory = Trajectory(trajectoryConfig)
                        fire(TrajectoryRecalculated)
                }
            }
            button {
                text = "Run trajectory"
                action {
                    if (trajectory.isValid) link.run(trajectory)
                    else error("Trajectory is invalid!")
                }
            }
        }
        vbox {
            addClass(Style.wrapper)
            vbox {
                label("Distance (rotations): $INITIAL_DISTANCE") {
                    subscribe<DistanceSliderValueChanged> {
                        text = "Distance (rotations): ${it.newValue}"
                    }
                }
                slider(MIN_DISTANCE, MAX_DISTANCE, INITIAL_DISTANCE) {
                    minorTickCount = 10
                    majorTickUnit = 50.0
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
                label("Max Velocity (rotations / sec): $INITIAL_VELOCITY") {
                    subscribe<VelocitySliderValueChanged> {
                        text = "Max Velocity (rotations / sec): ${it.newValue}"
                    }
                }
                slider(MIN_VELOCITY, MAX_VELOCITY, INITIAL_VELOCITY) {
                    minorTickCount = 10
                    majorTickUnit = 10.0
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
                label("Max Acceleration (rotations / sec ^ 2): $INITIAL_ACCELERATION") {
                    subscribe<AccelerationSliderValueChanged> {
                        text = "Max Acceleration (rotations / sec ^ 2): ${it.newValue}"
                    }
                }
                slider(MIN_ACCELERATION, MAX_ACCELERATION, INITIAL_ACCELERATION) {
                    minorTickCount = 10
                    majorTickUnit = 10.0
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
                label("Max Jerk (rotations / sec ^ 3): $INITIAL_JERK") {
                    subscribe<JerkSliderValueChanged> {
                        text = "Max Jerk (rotations / sec ^ 3): ${it.newValue}"
                    }
                }
                slider(MIN_JERK, MAX_JERK, INITIAL_JERK) {
                    minorTickCount = 10
                    majorTickUnit = 10.0
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
