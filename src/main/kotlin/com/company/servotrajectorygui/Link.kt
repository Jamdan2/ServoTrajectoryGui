package com.company.servotrajectorygui

import org.ardulink.core.Link
import org.ardulink.core.Pin
import org.ardulink.core.convenience.Links

val link: Link = Links.getDefault()

val speedPin = Pin.analogPin(9)
val enablePin = Pin.digitalPin(8)
