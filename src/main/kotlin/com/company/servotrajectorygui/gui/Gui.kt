package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.*
import javafx.event.EventHandler
import javafx.scene.chart.NumberAxis
import javafx.stage.Stage
import tornadofx.*
import kotlin.math.roundToInt

class Gui : App(MainView::class, Style::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
    }
}
