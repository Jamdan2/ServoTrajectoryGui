package com.company.servotrajectorygui

import com.company.servotrajectorygui.gui.Gui
import com.company.servotrajectorygui.trajectory.supplyMinimum
import com.company.servotrajectorygui.trajectory.trajectory
import com.company.servotrajectorygui.trajectory.v
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import tornadofx.launch

fun main(args: Array<String>) {
    supplyMinimum()
    launch<Gui>()
}
