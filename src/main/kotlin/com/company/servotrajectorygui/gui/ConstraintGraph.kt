package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.roundTo
import com.company.servotrajectorygui.trajectory.a
import com.company.servotrajectorygui.virtualTimer
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*

class ConstraintGraph : Fragment() {
    private val constraintName: String? by params
    private val graphStyles: CssRule? by params
    private val constraintUnit: String? by params
    private val graphingFunction: (XYChart.Series<Number, Number>.() -> Unit)? by params

    override val root = linechart(constraintName!!, NumberAxis(), NumberAxis()) {
        addClass(graphStyles!!)
        setPrefSize(250.0, 250.0)
        createSymbols = false
        isLegendVisible = false
        animated = false
        xAxis.label = "time"
        yAxis.label = constraintUnit!!
        series(constraintName!!) {
            graphingFunction!!()
            subscribe<TrajectoryRecalculated> {
                data.clear()
                graphingFunction!!()
            }
        }
    }
}
