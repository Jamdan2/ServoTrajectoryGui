package com.company.servotrajectorygui.gui

import tornadofx.*

class MainView : View("Servo Trajectory Gui") {
    override val root = borderpane {
        addClass(Styles.spaced)
        left {
            vbox {
                add<SliderView>()
                add<PidView>()
            }
        }
    }
}
