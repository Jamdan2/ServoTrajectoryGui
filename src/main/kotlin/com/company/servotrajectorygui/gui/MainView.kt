package com.company.servotrajectorygui.gui

import tornadofx.*

class MainView : View("Servo Trajectory Gui") {
    override val root = borderpane {
        padding = insets(10)
        addClass(Styles.spaced)
        left {
            vbox {
                addClass(Styles.spaced)
                add<ControlsView>()
                add<SliderView>()
                add<PidView>()
            }
        }
        right<GraphView>()
    }
}
