package com.company.servotrajectorygui.gui

import tornadofx.*

class PidView : View() {
    private val pidController by inject<PidController>()

    private val checkbox = checkbox()

    private val kpTextfield = find<PidConstantTextfield>(
            "constantName" to "Kp",
            "initialValue" to 1.0
    )

    private val kiTextfield = find<PidConstantTextfield>(
            "constantName" to "Ki",
            "initialValue" to 0.0
    )

    private val kdTextfield = find<PidConstantTextfield>(
            "constantName" to "Kd",
            "initialValue" to 0.0
    )

    init {
        checkbox.selectedProperty().onChange {
            pidController.isPidUsed = it
        }
        kpTextfield.valueProperty.onChange {
            pidController.configureKp(it)
        }
        kiTextfield.valueProperty.onChange {
            pidController.configureKi(it)
        }
        kdTextfield.valueProperty.onChange {
            pidController.configureKd(it)
        }
    }

    override val root = vbox {
        addClass(Styles.spaced)
        hbox {
            add(checkbox)
            label("use PID ")
        }
        vbox {
            visibleProperty().bindBidirectional(checkbox.selectedProperty())
            addClass(Styles.spaced)
            add(kpTextfield)
            add(kiTextfield)
            add(kdTextfield)
        }
    }
}
