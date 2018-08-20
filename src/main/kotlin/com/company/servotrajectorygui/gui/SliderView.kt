package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.*
import tornadofx.View
import tornadofx.addClass
import tornadofx.onChange
import tornadofx.vbox
import kotlin.math.roundToInt

class SliderView : View() {
    private val trajectoryController by inject<TrajectoryController>()

    private val distanceSlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "distance",
            "unitName" to "rotations",
            "minValue" to MIN_DISTANCE,
            "maxValue" to MAX_DISTANCE,
            "initialValue" to INITIAL_DISTANCE,
            "format" to "%.0f"
    ))

    private val velocitySlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "max velocity",
            "unitName" to "rotations / second",
            "minValue" to MIN_VELOCITY,
            "maxValue" to MAX_VELOCITY,
            "initialValue" to INITIAL_VELOCITY,
            "format" to "%.3f"
    ))

    private val accelerationSlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "max acceleration",
            "unitName" to "rotations / second^2",
            "minValue" to MIN_ACCELERATION,
            "maxValue" to MAX_ACCELERATION,
            "initialValue" to INITIAL_ACCELERATION,
            "format" to "%.3f"
    ))

    private val jerkSlider = find<ConstraintSlider>(mapOf(
            "constraintName" to "max jerk",
            "unitName" to "rotations / second^3",
            "minValue" to MIN_JERK,
            "maxValue" to MAX_JERK,
            "initialValue" to INITIAL_JERK,
            "format" to "%.3f"
    ))

    init {
        distanceSlider.valueProperty.onChange {
            if (it > 0.0) trajectoryController.configureDistance(it.roundToInt())
        }
        velocitySlider.valueProperty.onChange {
            if (it > 0.0) trajectoryController.configureVelocity(it)
        }
        accelerationSlider.valueProperty.onChange {
            if (it > 0.0) trajectoryController.configureAcceleration(it)
        }
        jerkSlider.valueProperty.onChange {
            if (it > 0.0) trajectoryController.configureJerk(it)
        }
    }

    override val root = vbox {
        addClass(Styles.spaced)
        add(distanceSlider)
        add(velocitySlider)
        add(accelerationSlider)
        add(jerkSlider)
    }
}
