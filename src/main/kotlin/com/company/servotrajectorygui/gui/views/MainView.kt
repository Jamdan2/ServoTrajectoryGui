package com.company.servotrajectorygui.gui.views

import com.company.servotrajectorygui.gui.styles.Style
import tornadofx.*

class MainView : View() {
    override val root = borderpane {
        title = "Servo Trajectory Gui"
        addClass(Style.wrapper)
        left<SliderView>()
        right<GraphView>()
    }
}
