package com.company.servotrajectorygui.gui

import tornadofx.EventBus
import tornadofx.FXEvent

object TrajectoryRecalculated : FXEvent(EventBus.RunOn.ApplicationThread)
