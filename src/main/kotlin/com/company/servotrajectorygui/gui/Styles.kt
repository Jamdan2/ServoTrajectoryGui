package com.company.servotrajectorygui.gui

import javafx.scene.paint.Paint
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val spaced by cssclass()
        val distanceChart by cssclass()
        val velocityChart by cssclass()
        val accelerationChart by cssclass()
        val jerkChart by cssclass()
    }

    init {
        spaced {
            spacing = 3.mm
        }
        distanceChart {
            chartSeriesLine {
                stroke = Paint.valueOf("#000000")
            }
        }
        velocityChart {
            chartSeriesLine {
                stroke = Paint.valueOf("#179b60")
            }
        }
        accelerationChart {
            chartSeriesLine {
                stroke = Paint.valueOf("#1464ba")
            }
        }
        jerkChart {
            chartSeriesLine {
                stroke = Paint.valueOf("#543eb7")
            }
        }
    }
}
