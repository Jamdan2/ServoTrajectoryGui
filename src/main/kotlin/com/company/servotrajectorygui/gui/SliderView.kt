package com.company.servotrajectorygui.gui

import tornadofx.View
import tornadofx.addClass
import tornadofx.vbox

class SliderView : View() {
    private val distanceSlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "distance",
            "unitName" to "rotations",
            "minValue" to 0,
            "maxValue" to 500,
            "initialValue" to 250,
            "format" to "%.0f"
    ))

    private val velocitySlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "velocity",
            "unitName" to "rotations / second",
            "minValue" to 0,
            "maxValue" to 100,
            "initialValue" to 50,
            "format" to "%.3f"
    ))

    private val accelerationSlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "acceleration",
            "unitName" to "rotations / second^2",
            "minValue" to 0,
            "maxValue" to 100,
            "initialValue" to 50,
            "format" to "%.3f"
    ))

    private val jerkSlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "jerk",
            "unitName" to "rotations / second^3",
            "minValue" to 0,
            "maxValue" to 100,
            "initialValue" to 50,
            "format" to "%.3f"
    ))

    override val root = vbox {
        addClass(Styles.spaced)
        add(distanceSlider)
        add(velocitySlider)
        add(accelerationSlider)
        add(jerkSlider)
    }
}
