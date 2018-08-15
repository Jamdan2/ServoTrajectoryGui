package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.trajectory.Trajectory
import com.company.servotrajectorygui.trajectory.TrajectoryConfig
import tornadofx.Controller

class TrajectoryController : Controller() {
    var trajectoryConfig = TrajectoryConfig(250, 50.0, 50.0, 50.0)

    var trajectory = Trajectory(trajectoryConfig)

    fun configureDistance(value: Int) {
        trajectoryConfig = TrajectoryConfig(
                value,
                trajectoryConfig.maxVelocity,
                trajectoryConfig.maxAcceleration,
                trajectoryConfig.maxJerk
        )
    }

    fun configureVelocity(value: Double) {
        trajectoryConfig = TrajectoryConfig(
                trajectoryConfig.distance,
                value,
                trajectoryConfig.maxAcceleration,
                trajectoryConfig.maxJerk
        )
    }

    fun configureAcceleration(value: Double) {
        trajectoryConfig = TrajectoryConfig(
                trajectoryConfig.distance,
                trajectoryConfig.maxVelocity,
                value,
                trajectoryConfig.maxJerk
        )
    }

    fun configureJerk(value: Double) {
        trajectoryConfig = TrajectoryConfig(
                trajectoryConfig.distance,
                trajectoryConfig.maxVelocity,
                trajectoryConfig.maxAcceleration,
                value
        )
    }

    fun calculateTrajectory() {
        trajectory = Trajectory(trajectoryConfig)
        fire(TrajectoryRecalculated)
    }
}