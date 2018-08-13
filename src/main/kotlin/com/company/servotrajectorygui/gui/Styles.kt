package com.company.servotrajectorygui.gui

import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.mm

class Styles : Stylesheet() {
    companion object {
        val spaced by cssclass()
    }

    init {
        spaced {
            spacing = 3.mm
        }
    }
}
