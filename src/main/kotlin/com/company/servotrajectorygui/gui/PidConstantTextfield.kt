package com.company.servotrajectorygui.gui

import javafx.beans.property.SimpleDoubleProperty
import tornadofx.*

class PidConstantTextfield : Fragment() {
    val valueProperty = SimpleDoubleProperty(0.0)

    private val constantName: String? by params
    private val initialValue: Double? by params

    private val label = label("${constantName!!}: ")

    init {
        valueProperty.set(initialValue!!)
    }

    private val textfield = textfield("0") {
        filterInput { it.controlNewText.isDouble() }
        textProperty().onChange {
            if (it != null && it != "") valueProperty.set(it.toDouble())
        }
    }

    override val root = hbox {
        add(label)
        add(textfield)
    }
}
