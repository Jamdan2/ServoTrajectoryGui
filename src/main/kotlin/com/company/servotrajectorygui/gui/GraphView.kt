package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.trajectory
import javafx.scene.chart.NumberAxis
import tornadofx.*

class GraphView : View() {
    override val root = vbox {
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
