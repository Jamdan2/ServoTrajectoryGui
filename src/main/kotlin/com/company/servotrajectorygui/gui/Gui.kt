package com.company.servotrajectorygui.gui

import com.company.servotrajectorygui.gui.controllers.LinkController
import com.company.servotrajectorygui.gui.styles.Style
import com.company.servotrajectorygui.gui.views.MainView
import javafx.stage.Stage
import tornadofx.*

class Gui : App(MainView::class, Style::class) {
    val linkController: LinkController by inject()

    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
        linkController.connect()
    }
}
