package com.company.servotrajectorygui.gui

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.layout.GridPane.setHgrow
import javafx.scene.layout.GridPane.setVgrow
import javafx.scene.layout.Priority
import tornadofx.*

class ConstraintSlider : Fragment() {
    val trajectoryController by inject<TrajectoryController>()

    val valueProperty = SimpleDoubleProperty(0.0)
    val value: Double get() = valueProperty.get()

    private val constraintName: String? by params
    private val unitName: String? by params
    private val minValue: Number? by params
    private val maxValue: Number? by params
    private val initialValue: Number? by params
    private val format: String? by params

    private val label = label("")

    private val slider = slider(minValue, maxValue, initialValue) {
        label.textProperty().bind(Bindings.concat(
                "$constraintName ($unitName): ",
                Bindings.format(format, valueProperty())
        ))
        valueProperty.bindBidirectional(valueProperty())
        prefWidth = 500.1
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = (maxValue!!.toDouble() - minValue!!.toDouble()) / 10
        minorTickCount = 9
        valueProperty().onChange {
            if (it != 0.0)
            trajectoryController.calculateTrajectory()
        }
    }

    override val root = vbox {
        add(label)
        add(slider)
    }
}
