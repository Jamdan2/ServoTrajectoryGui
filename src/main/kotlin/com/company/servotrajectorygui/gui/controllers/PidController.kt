package com.company.servotrajectorygui.gui.controllers

import com.company.servotrajectorygui.PidConfig
import tornadofx.Controller

class PidController : Controller() {
    var pidConfig = PidConfig(0.0, 0.0, 0.0)
}
