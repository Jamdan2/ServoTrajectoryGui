package com.company.servotrajectorygui

import com.company.servotrajectorygui.gui.Gui
import com.company.servotrajectorygui.trajectory.listenToFeedback
import com.company.servotrajectorygui.trajectory.supplyMinimum
import kotlinx.coroutines.experimental.launch
import org.ardulink.core.events.AnalogPinValueChangedEvent
import org.ardulink.core.events.DigitalPinValueChangedEvent
import org.ardulink.core.events.EventListener
import tornadofx.launch

fun main(args: Array<String>) {
    link.supplyMinimum()
    link.listenToFeedback()
    launch<Gui>()
}
