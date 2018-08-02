package com.company.servotrajectorygui.gui

import javafx.stage.Stage
import tornadofx.*

class Gui : App(MainView::class, Style::class) {
    val linkController: LinkController by inject()

    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
        linkController.supplyMinimumVoltage()
        linkController.listenToFeedback()
    }
}
