package com.company.servotrajectorygui.gui

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.mm

class Style : Stylesheet() {
    companion object {
        val wrapper by cssclass()
    }
    init {
        root {
            padding = box(5.mm)
        }
        slider {
            prefWidth = 150.mm
        }
        wrapper {
            spacing = 3.mm
        }
    }
}
