package com.company.servotrajectorygui

import com.company.servotrajectorygui.gui.Gui
import kotlinx.coroutines.experimental.launch
import tornadofx.launch

fun main(args: Array<String>) {
    launch<Gui>(args)
}
