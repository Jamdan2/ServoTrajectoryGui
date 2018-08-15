package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.virtualTimer
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*

class ConstraintGraph : Fragment() {
    private val trajectoryController by inject<TrajectoryController>()

    private val constraintName: String? by params
    private val constraintFunction: ((Double) -> Double)? by params
    private val graphStyles: CssRule? by params
    private val graphingMethod: GraphingMethod? by params

    enum class GraphingMethod {
        POINT_ITERATION,
        VITAL_POINT,
        VERTICAL_SUPPORT
    }

    private fun XYChart.Series<Number, Number>.graph() {
        data.clear()
        when (graphingMethod!!) {
            GraphingMethod.POINT_ITERATION -> {
                virtualTimer(trajectoryController.trajectory.t7, trajectoryController.trajectory.t7 / 150) {
                    data(it, constraintFunction!!(it))
                }
                data(trajectoryController.trajectory.t7, constraintFunction!!(trajectoryController.trajectory.t7))
            }
            GraphingMethod.VITAL_POINT -> {
                data(0.0, constraintFunction!!(0.0))
                with(trajectoryController.trajectory.t1) { data(this, constraintFunction!!(this)) }
                with(trajectoryController.trajectory.t2) { data(this, constraintFunction!!(this)) }
                with(trajectoryController.trajectory.t3) { data(this, constraintFunction!!(this)) }
                with(trajectoryController.trajectory.t4) { data(this, constraintFunction!!(this)) }
                with(trajectoryController.trajectory.t5) { data(this, constraintFunction!!(this)) }
                with(trajectoryController.trajectory.t6) { data(this, constraintFunction!!(this)) }
                with(trajectoryController.trajectory.t7) { data(this, constraintFunction!!(this)) }
            }
            GraphingMethod.VERTICAL_SUPPORT -> {
                data(0.0, constraintFunction!!(0.0))
                with(trajectoryController.trajectory.t1) { data(0.0, constraintFunction!!(this)) }
                with(trajectoryController.trajectory.t1) { data(this, constraintFunction!!(this)) }
                data(trajectoryController.trajectory.t1, constraintFunction!!(trajectoryController.trajectory.t2))
                with(trajectoryController.trajectory.t2) { data(this, constraintFunction!!(this)) }
                data(trajectoryController.trajectory.t2, constraintFunction!!(trajectoryController.trajectory.t3))
                with(trajectoryController.trajectory.t3) { data(this, constraintFunction!!(this)) }
                data(trajectoryController.trajectory.t3, constraintFunction!!(trajectoryController.trajectory.t4))
                with(trajectoryController.trajectory.t4) { data(this, constraintFunction!!(this)) }
                data(trajectoryController.trajectory.t4, constraintFunction!!(trajectoryController.trajectory.t5))
                with(trajectoryController.trajectory.t5) { data(this, constraintFunction!!(this)) }
                data(trajectoryController.trajectory.t5, constraintFunction!!(trajectoryController.trajectory.t6))
                with(trajectoryController.trajectory.t6) { data(this, constraintFunction!!(this)) }
                data(trajectoryController.trajectory.t6, constraintFunction!!(trajectoryController.trajectory.t7))
                with(trajectoryController.trajectory.t7) { data(this, constraintFunction!!(this)) }
            }
        }
    }

    override val root = linechart(constraintName!!, NumberAxis(), NumberAxis()) {
        addClass(graphStyles!!)
        setPrefSize(250.0, 250.0)
        createSymbols = false
        isLegendVisible = false
        animated = false
        series(constraintName!!) {
            graph()
            subscribe<TrajectoryRecalculated> { graph() }
        }
    }
}
