package com.company.servotrajectorygui.gui.controllers

import com.company.servotrajectorygui.INITIAL_ACCELERATION
import com.company.servotrajectorygui.INITIAL_DISTANCE
import com.company.servotrajectorygui.INITIAL_JERK
import com.company.servotrajectorygui.INITIAL_VELOCITY
import com.company.servotrajectorygui.trajectory.Trajectory
import com.company.servotrajectorygui.trajectory.TrajectoryConfig
import tornadofx.Controller

class TrajectoryController : Controller() {
    var trajectoryConfig = TrajectoryConfig(
            INITIAL_DISTANCE,
            INITIAL_VELOCITY,
            INITIAL_ACCELERATION,
            INITIAL_JERK
    )

    var trajectory = Trajectory(trajectoryConfig)
        private set

    fun syncTrajectoryWithConfig() {
        trajectory = Trajectory(trajectoryConfig)
    }
}
