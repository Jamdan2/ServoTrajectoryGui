package com.company.servotrajectorygui.gui.styles

import tornadofx.*

class Style : Stylesheet() {
    companion object {
        val wrapper by cssclass()
        val distanceChart by cssclass()
        val velocityChart by cssclass()
        val accelerationChart by cssclass()
        val jerkChart by cssclass()
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
        chartSymbol {
            backgroundRadius = multi(box(2.px))
            padding = box(1.px)
            backgroundColor = multi(c("#000000"))
        }
        distanceChart {
            chartSymbol {
                backgroundColor = multi(c("#000000"))
            }
        }
        velocityChart {
            chartSymbol {
                backgroundColor = multi(c("#00a002"))
            }
        }
        accelerationChart {
            chartSymbol {
                backgroundColor = multi(c("#0055ff"))
            }
        }
        jerkChart {
            chartSymbol {
                backgroundColor = multi(c("#7f00ff"))
            }
        }
    }
}
