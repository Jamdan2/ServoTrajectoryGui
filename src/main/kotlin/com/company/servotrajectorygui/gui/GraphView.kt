@file:Suppress("NestedLambdaShadowedImplicitParameter")
package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.trajectory.*
import com.company.servotrajectorygui.virtualTimer
import javafx.scene.chart.NumberAxis
import tornadofx.*

class GraphView : View() {
    override val root = vbox {
        hbox {
            scatterchart("Distance", NumberAxis(), NumberAxis()) {
                addClass(Style.distanceChart)
                setPrefSize(250.0, 250.0)
                series("Distance") {
                    virtualTimer(trajectory.t7, 0.01) {
                        data(it, trajectory.d(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectory.t7, 0.01) {
                            data(it, trajectory.d(it))
                        }
                    }
                }
            }
            scatterchart("Velocity", NumberAxis(), NumberAxis()) {
                addClass(Style.velocityChart)
                setPrefSize(250.0, 250.0)
                series("Velocity") {
                    virtualTimer(trajectory.t7, 0.01) {
                        data(it, trajectory.v(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectory.t7, 0.01) {
                            data(it, trajectory.v(it))
                        }
                    }
                }
            }
        }
        hbox {
            scatterchart("Acceleration", NumberAxis(), NumberAxis()) {
                addClass(Style.accelerationChart)
                setPrefSize(250.0, 250.0)
                series("Acceleration") {
                    virtualTimer(trajectory.t7, 0.01) {
                        data(it, trajectory.a(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectory.t7, 0.01) {
                            data(it, trajectory.a(it))
                        }
                    }
                }
            }
            scatterchart("Jerk", NumberAxis(), NumberAxis()) {
                addClass(Style.jerkChart)
                setPrefSize(250.0, 250.0)
                series("Jerk") {
                    virtualTimer(trajectory.t7, 0.01) {
                        data(it, trajectory.j(it))
                    }
                    subscribe<TrajectoryRecalculated> {
                        data.clear()
                        virtualTimer(trajectory.t7, 0.01) {
                            data(it, trajectory.j(it))
                        }
                    }
                }
            }
        }
    }
}
