package com.trianglz.mimar.common.domain.extention


import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.toHourMinutes24Format(): String = DateTimeFormatter.ofPattern("HH:mm").format(this)

fun LocalTime?.toFormattedHourMinutes12Format(): String =
    DateTimeFormatter.ofPattern("hh:mm a").format(this)