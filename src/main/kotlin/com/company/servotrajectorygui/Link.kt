package com.company.servotrajectorygui

import org.ardulink.core.Link
import org.ardulink.core.convenience.Links

val link: Link = Links.getDefault()

fun Link.setServoAngle(angle: Int) = sendCustomMessage("ssrv", when {
    angle < 10 -> "00$angle"
    angle < 100 -> "0$angle"
    else -> "$angle"
})
