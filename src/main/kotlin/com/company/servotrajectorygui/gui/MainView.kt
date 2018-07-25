package com.company.servotrajectorygui.gui

import tornadofx.*

class MainView : View() {
    override val root = borderpane {
        title = "Servo Trajectory Gui"
        addClass(Style.wrapper)
        left<SliderView>()
        right<GraphView>()
    }
}
