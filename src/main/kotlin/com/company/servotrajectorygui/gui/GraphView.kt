package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.trajectory.a
import com.company.servotrajectorygui.trajectory.d
import com.company.servotrajectorygui.trajectory.j
import com.company.servotrajectorygui.trajectory.v
import com.company.servotrajectorygui.virtualTimer
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*
import java.util.*

class GraphView : View() {
    private val trajectoryController by inject<TrajectoryController>()

    private val distanceGraph = find<ConstraintGraph>(
            "constraintName" to "distance",
            "constraintFunction" to { t: Double -> trajectoryController.trajectory.d(t) },
            "graphStyles" to Styles.distanceChart,
            "graphingMethod" to ConstraintGraph.GraphingMethod.POINT_ITERATION,
            "constraintUnit" to "rotations"
    )

    private val velocityGraph = find<ConstraintGraph>(
            "constraintName" to "velocity",
            "constraintFunction" to { t: Double -> trajectoryController.trajectory.v(t) },
            "graphStyles" to Styles.velocityChart,
            "graphingMethod" to ConstraintGraph.GraphingMethod.POINT_ITERATION,
            "constraintUnit" to "rotations / second"
    )

    private val accelerationGraph = find<ConstraintGraph>(
            "constraintName" to "acceleration",
            "constraintFunction" to { t: Double -> trajectoryController.trajectory.a(t) },
            "graphStyles" to Styles.accelerationChart,
            "graphingMethod" to ConstraintGraph.GraphingMethod.VITAL_POINT,
            "constraintUnit" to "rotations / second^2"
    )

    private val jerkGraph = find<ConstraintGraph>(
            "constraintName" to "jerk",
            "constraintFunction" to { t: Double -> trajectoryController.trajectory.j(t) },
            "graphStyles" to Styles.jerkChart,
            "graphingMethod" to ConstraintGraph.GraphingMethod.VERTICAL_SUPPORT,
            "constraintUnit" to "rotations / second^3"
    )

    override val root = vbox {
        hbox {
            add(distanceGraph)
            add(velocityGraph)
        }
        hbox {
            add(accelerationGraph)
            add(jerkGraph)
        }
    }
}
