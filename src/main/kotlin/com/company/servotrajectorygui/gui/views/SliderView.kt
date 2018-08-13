package com.company.servotrajectorygui.gui.views

import com.company.servotrajectorygui.*
import com.company.servotrajectorygui.gui.controllers.LinkController
import com.company.servotrajectorygui.gui.controllers.PidController
import com.company.servotrajectorygui.gui.controllers.TrajectoryController
import com.company.servotrajectorygui.gui.events.*
import com.company.servotrajectorygui.gui.styles.Style
import com.company.servotrajectorygui.trajectory.*
import tornadofx.*

class SliderView : View() {
    private val linkController by inject<LinkController>()
    private val trajectoryController by inject<TrajectoryController>()
    private val pidController by inject<PidController>()

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
                        linkController.runTrajectory(trajectoryController.trajectory, pidController.pidConfig)
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
                label("Max Velocity (rotations / second): $INITIAL_VELOCITY") {
                    subscribe<VelocitySliderValueChanged> {
                        text = "Max Velocity (rotations / second): ${it.newValue}"
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
                label("Max Acceleration (rotations / second^2): $INITIAL_ACCELERATION") {
                    subscribe<AccelerationSliderValueChanged> {
                        text = "Max Acceleration (rotations / second^2): ${it.newValue}"
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
                label("Max Jerk (rotations / second^3): $INITIAL_JERK") {
                    subscribe<JerkSliderValueChanged> {
                        text = "Max Jerk (rotations / second^3): ${it.newValue}"
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
            hbox {
                addClass(Style.wrapper)
                addClass(Style.textFields)
                hbox {
                    label("Kp: ")
                    textfield("0") {
                        filterInput { it.controlNewText.isDouble() }
                        textProperty().addListener { _, _, newValue ->
                            if (newValue != "") {
                                with(pidController.pidConfig) {
                                    pidController.pidConfig = PidConfig(newValue.toDouble(), ki, kd)
                                }
                            }
                        }
                    }
                }
                hbox {
                    label("Ki: ")
                    textfield("0") {
                        filterInput { it.controlNewText.isDouble() }
                        textProperty().addListener { _, _, newValue ->
                            if (newValue != "") {
                                with(pidController.pidConfig) {
                                    pidController.pidConfig = PidConfig(kp, newValue.toDouble(), kd)
                                }
                            }
                        }
                    }
                }
                hbox {
                    label("Kd: ")
                    textfield("0") {
                        filterInput { it.controlNewText.isDouble() }
                        textProperty().addListener { _, _, newValue ->
                            if (newValue != "") {
                                with(pidController.pidConfig) {
                                    pidController.pidConfig = PidConfig(kp, ki, newValue.toDouble())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
