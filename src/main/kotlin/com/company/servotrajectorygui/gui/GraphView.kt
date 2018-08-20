package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.roundTo
import com.company.servotrajectorygui.trajectory.a
import com.company.servotrajectorygui.trajectory.d
import com.company.servotrajectorygui.trajectory.j
import com.company.servotrajectorygui.trajectory.v
import com.company.servotrajectorygui.virtualTimer
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import tornadofx.*
import java.util.*

class GraphView : View() {
    private val trajectoryController by inject<TrajectoryController>()

    private val graphDistance: XYChart.Series<Number, Number>.() -> Unit = {
        virtualTimer(trajectoryController.trajectory.t7, trajectoryController.trajectory.t7 / 150) {
            data(it, trajectoryController.trajectory.d(it))
        }
        data(trajectoryController.trajectory.t7, trajectoryController.trajectory.d(trajectoryController.trajectory.t7))
    }

    private val graphVelocity: XYChart.Series<Number, Number>.() -> Unit = {
        virtualTimer(trajectoryController.trajectory.t7, trajectoryController.trajectory.t7 / 150) {
            data(it, trajectoryController.trajectory.v(it))
        }
        data(trajectoryController.trajectory.t7, trajectoryController.trajectory.v(trajectoryController.trajectory.t7))
    }

    private val graphAcceleration: XYChart.Series<Number, Number>.() -> Unit = {
        with(trajectoryController.trajectory) {
            data(0.0, a(0.0))
            data(t1, a(t1))
            data(t2, a(t2))
            data(t3, a(t3))
            data(t4, a(t4))
            data(t5, a(t5))
            data(t6, a(t6))
            data(t7, a(t7))
        }
    }

    private val graphJerk: XYChart.Series<Number, Number>.() -> Unit = {
        with(trajectoryController.trajectory) {
            data(0.0, 0.0)
            data(0.0, j)
            data(t1.roundTo(8), j)
            if (t1.roundTo(8) != t2.roundTo(8)) {
                data(t1.roundTo(8), 0.0)
                data(t2.roundTo(8), 0.0)
            }
            data(t2.roundTo(8), -j)
            data(t3.roundTo(8), -j)
            if (t3.roundTo(8) != t4.roundTo(8)) {
                data(t3.roundTo(8), 0.0)
                data(t4.roundTo(8), 0.0)
            }
            data(t4.roundTo(8), -j)
            data(t5.roundTo(8), -j)
            if (t5.roundTo(8) != t6.roundTo(8)) {
                data(t5.roundTo(8), 0.0)
                data(t6.roundTo(8), 0.0)
            }
            data(t6.roundTo(8), j)
            data(t7.roundTo(8), j)
            data(t7.roundTo(8), 0.0)
        }
    }

    private val distanceGraph = find<ConstraintGraph>(
            "constraintName" to "distance",
            "graphStyles" to Styles.distanceChart,
            "constraintUnit" to "rotations",
            "graphingFunction" to graphDistance
    )

    private val velocityGraph = find<ConstraintGraph>(
            "constraintName" to "velocity",
            "graphStyles" to Styles.velocityChart,
            "constraintUnit" to "rotations / second",
            "graphingFunction" to graphVelocity
    )

    private val accelerationGraph = find<ConstraintGraph>(
            "constraintName" to "acceleration",
            "graphStyles" to Styles.accelerationChart,
            "constraintUnit" to "rotations / second^2",
            "graphingFunction" to graphAcceleration
    )

    private val jerkGraph = find<ConstraintGraph>(
            "constraintName" to "jerk",
            "graphStyles" to Styles.jerkChart,
            "constraintUnit" to "rotations / second^3",
            "graphingFunction" to graphJerk
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
