package com.company.servotrajectorygui.gui.events

import tornadofx.EventBus
import tornadofx.FXEvent

class DistanceSliderValueChanged(val newValue: Int) : FXEvent(EventBus.RunOn.ApplicationThread)
class VelocitySliderValueChanged(val newValue: Double) : FXEvent(EventBus.RunOn.ApplicationThread)
class AccelerationSliderValueChanged(val newValue: Double) : FXEvent(EventBus.RunOn.ApplicationThread)
class JerkSliderValueChanged(val newValue: Double) : FXEvent(EventBus.RunOn.ApplicationThread)

object TrajectoryRecalculated : FXEvent(EventBus.RunOn.ApplicationThread)
