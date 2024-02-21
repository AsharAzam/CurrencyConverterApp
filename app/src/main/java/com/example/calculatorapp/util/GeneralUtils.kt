package com.example.calculatorapp.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

 fun LocalDateTime.dateToString(format: String): String {
     val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())

    return formatter.format(this)
}