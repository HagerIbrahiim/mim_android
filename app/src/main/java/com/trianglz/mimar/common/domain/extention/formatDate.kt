package com.trianglz.mimar.common.domain.extention


import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun String?.getLocalDateFromISO(): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE
        LocalDate.parse(this, formatter)
    } catch (ex: Exception) {
        null
    }
}