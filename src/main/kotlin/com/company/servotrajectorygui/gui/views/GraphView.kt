@file:Suppress("NestedLambdaShadowedImplicitParameter")
package com.company.servotrajectorygui.gui.views

import com.company.servotrajectorygui.gui.styles.Style
import com.company.servotrajectorygui.gui.controllers.TrajectoryController
import com.company.servotrajectorygui.gui.events.TrajectoryRecalculated
import com.company.servotrajectorygui.trajectory.*
import com.company.servotrajectorygui.virtualTimer
import javafx.scene.chart.NumberAxis
import tornadofx.*

class GraphView : View() {
    private val trajectoryController by inject<TrajectoryController>()

    override val root = vbox {
        hbox {
            scatterchart("Distance", NumberAxis(), NumberAxis()) {
                addClass(Style.distanceChart)
                xAxis.label = "seconds"
                yAxis.label = "rotations"
                isLegendVisible = false
                setPrefSize(250.0, 250.0)
                series("Distance") {
                    virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                        data(it, trajectoryController.trajectory.d(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                            data(it, trajectoryController.trajectory.d(it))
                        }
                    }
                }
            }
            scatterchart("Velocity", NumberAxis(), NumberAxis()) {
                addClass(Style.velocityChart)
                xAxis.label = "seconds"
                yAxis.label = "rotations / second"
                isLegendVisible = false
                setPrefSize(250.0, 250.0)
                series("Velocity") {
                    virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                        data(it, trajectoryController.trajectory.v(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                            data(it, trajectoryController.trajectory.v(it))
                        }
                    }
                }
            }
        }
        hbox {
            scatterchart("Acceleration", NumberAxis(), NumberAxis()) {
                addClass(Style.accelerationChart)
                xAxis.label = "seconds"
                yAxis.label = "rotations / second^2"
                isLegendVisible = false
                setPrefSize(250.0, 250.0)
                series("Acceleration") {
                    virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                        data(it, trajectoryController.trajectory.a(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                            data(it, trajectoryController.trajectory.a(it))
                        }
                    }
                }
            }
            scatterchart("Jerk", NumberAxis(), NumberAxis()) {
                addClass(Style.jerkChart)
                xAxis.label = "seconds"
                yAxis.label = "rotations / second^3"
                isLegendVisible = false
                setPrefSize(250.0, 250.0)
                series("Jerk") {
                    virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                        data(it, trajectoryController.trajectory.j(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectoryController.trajectory.t7, 0.01) {
                            data(it, trajectoryController.trajectory.j(it))
                        }
                    }
                }
            }
        }
    }
}
