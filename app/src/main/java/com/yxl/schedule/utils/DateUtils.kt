package com.yxl.schedule.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Calendar

object DateUtils {

    val now = LocalDate.now()
    const val week = Calendar.WEEK_OF_MONTH

    val weekOfMonth = when(week + 1){
        1 -> 2
        2 -> 3
        3 -> 4
        4 -> 1
        else -> {0}
    }

    fun setUpDate(position: Int, current: Int): String?{
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val day = LocalDate.now().dayOfWeek.value
        val date = LocalDate.now().minusDays((day - 1).toLong())
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