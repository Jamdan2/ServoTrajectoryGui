package com.company.servotrajectorygui.gui

import tornadofx.View
import tornadofx.addClass
import tornadofx.borderpane
import tornadofx.left

class MainView : View("Servo Trajectory Gui") {
    override val root = borderpane {
        addClass(Styles.spaced)
        left {
            top<SliderView>()
        }
    }
}
