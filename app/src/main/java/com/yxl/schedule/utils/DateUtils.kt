package com.yxl.schedule.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

object DateUtils {

    private val now = LocalDate.now()
    var week = Calendar.WEEK_OF_MONTH

    fun setUpDate(position: Int, current: Int): String?{
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val day = now.dayOfWeek.value
        val date = now.minusDays((day - 1).toLong())
        val startWeek: String
        val endWeek: String

        for(i in 0 .. 3){

            return when {
                position < current -> {
                    startWeek = date.minusWeeks((current - position).toLong()).format(formatter)
                    endWeek = date.minusWeeks((current - position).toLong()).plusDays(6).format(formatter)
                    "${startWeek.subSequence(0, 5)} - $endWeek"
                }

                position > current -> {
                    startWeek = date.plusWeeks((position - current).toLong()).format(formatter)
                    endWeek = date.plusWeeks((position - current).toLong()).plusDays(6).format(formatter)
                    "${startWeek.subSequence(0, 5)} - $endWeek"
                }

                else -> {
                    startWeek = date.format(formatter)
                    endWeek = date.plusDays(6).format(formatter)
                    "${startWeek.subSequence(0, 5)} - $endWeek"
                }
            }
        }
        return null
    }
}