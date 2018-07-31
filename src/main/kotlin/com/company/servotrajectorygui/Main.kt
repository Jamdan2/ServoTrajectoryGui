package com.company.servotrajectorygui

import com.company.servotrajectorygui.gui.Gui
import com.company.servotrajectorygui.trajectory.supplyMinimum
import tornadofx.launch

fun main(args: Array<String>) {
    supplyMinimum()
    launch<Gui>()
}
