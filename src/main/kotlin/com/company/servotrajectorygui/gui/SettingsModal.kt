package com.company.servotrajectorygui.gui

import tornadofx.*

class SettingsModal : Fragment("Settings") {
    override val root = vbox {
        padding = insets(10)
        addClass(Styles.spaced)
        hbox {
            label("enable pin number: ")
            textfield {
                filterInput { it.controlNewText.isInt() }
            }
        }
        hbox {
            label("motor pin number: ")
            textfield {
                filterInput { it.controlNewText.isInt() }
            }
        }
        hbox {
            label("sensor pin number: ")
            textfield {
                filterInput { it.controlNewText.isInt() }
            }
        }
    }
}
