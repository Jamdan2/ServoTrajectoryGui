package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.*
import com.company.servotrajectorygui.trajectory.*
import com.sun.org.apache.bcel.internal.Repository.addClass
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import sun.awt.image.ImageWatched
import tornadofx.*
import tornadofx.Stylesheet.Companion.label
import tornadofx.Stylesheet.Companion.slider
import kotlin.concurrent.thread
import kotlin.math.roundToInt

class SliderView : View() {
    private val linkController: LinkController by inject()
    private val trajectoryController: TrajectoryController by inject()

    override val root = vbox {
        hbox {
            addClass(Style.wrapper)
            button {
                text = "Calculate trajectory"
                action {
                        trajectoryController.syncTrajectoryWithConfig()
                        fire(TrajectoryRecalculated)
                }
            }
            button {
                text = "Run trajectory"
                action {
                    if (trajectoryController.trajectory.isValid) {
                        linkController.runTrajectory(trajectoryController.trajectory)
                    }
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
                        trajectoryController.trajectoryConfig = TrajectoryConfig(
                                newValue.toInt(),
                                trajectoryController.trajectoryConfig.maxVelocity,
                                trajectoryController.trajectoryConfig.maxAcceleration,
                                trajectoryController.trajectoryConfig.maxJerk
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
                        trajectoryController.trajectoryConfig = TrajectoryConfig(
                                trajectoryController.trajectoryConfig.distance,
                                newValue.toDouble(),
                                trajectoryController.trajectoryConfig.maxAcceleration,
                                trajectoryController.trajectoryConfig.maxJerk
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
                        trajectoryController.trajectoryConfig = TrajectoryConfig(
                                trajectoryController.trajectoryConfig.distance,
                                trajectoryController.trajectoryConfig.maxVelocity,
                                newValue.toDouble(),
                                trajectoryController.trajectoryConfig.maxJerk
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
                        trajectoryController.trajectoryConfig = TrajectoryConfig(
                                trajectoryController.trajectoryConfig.distance,
                                trajectoryController.trajectoryConfig.maxVelocity,
                                trajectoryController.trajectoryConfig.maxAcceleration,
                                newValue.toDouble()
                        )
                    }
                }
            }
        }
    }
}
