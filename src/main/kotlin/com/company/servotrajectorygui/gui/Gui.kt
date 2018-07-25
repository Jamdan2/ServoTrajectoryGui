package com.company.servotrajectorygui.gui

import javafx.stage.Stage
import tornadofx.*

class Gui : App(MainView::class, Style::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
    }
}
