package com.company.servotrajectorygui.gui

import javafx.geometry.Pos
import tornadofx.*

class SettingsModal : Fragment("Settings") {
    private val settingsController by inject<SettingsController>()

    var settings = settingsController.copySettings()

    override val root = vbox {
        padding = insets(10)
        addClass(Styles.spaced)
        hbox {
            label("enable pin number: ")
            textfield(settings.enablePinNum.toString()) {
                filterInput { it.controlNewText.isInt() }
                textProperty().onChange {
                    if (it != null && it != "") settings = settings.copy(enablePinNum = it.toInt())
                }
            }
        }
        hbox {
            label("motor pin number: ")
            textfield(settings.motorPinNum.toString()) {
                filterInput { it.controlNewText.isInt() }
                textProperty().onChange {
                    if (it != null && it != "") settings = settings.copy(motorPinNum = it.toInt())
                }
            }
        }
        hbox {
            label("sensor pin number: ")
            textfield(settings.sensorPinNum.toString()) {
                filterInput { it.controlNewText.isInt() }
                textProperty().onChange {
                    if (it != null && it != "") settings = settings.copy(sensorPinNum = it.toInt())
                }
            }
        }
        hbox {
            label("scale output: ")
            textfield(settings.minOutputPercentage.toString()) {
                prefWidth = 50.0
                filterInput { it.controlNewText.isDouble() }
                textProperty().onChange {
                    if (it != null && it != "") settings = settings.copy(minOutputPercentage = it.toDouble())
                }
            }
            label("% to ")
            textfield(settings.maxOutputPercentage.toString()) {
                prefWidth = 50.0
                filterInput { it.controlNewText.isDouble() }
                textProperty().onChange {
                    if (it != null && it != "") settings = settings.copy(maxOutputPercentage = it.toDouble())
                }
            }
            label("%")
        }
        hbox {
            addClass(Styles.spaced)
            button("okay") {
                alignment = Pos.BOTTOM_RIGHT
                action {
                    settingsController.applySettings(settings)
                    close()
                }
            }
            button("apply") {
                alignment = Pos.BOTTOM_RIGHT
                action {
                    settingsController.applySettings(settings)
                }
            }
            button("cancel") {
                alignment = Pos.BOTTOM_RIGHT
                action {
                    close()
                }
            }
        }
    }
}
