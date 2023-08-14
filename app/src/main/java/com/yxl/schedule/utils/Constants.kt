package com.yxl.schedule.utils

import java.util.Calendar

object Constants{

    const val scheduleBaseUrl = "https://schedule.elementfx.com/api/v1/schedule/"
    val dayNames = listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")
    val subgroups = listOf("1", "2")
    val weekOfMonth: Int
        get() = if(Calendar.WEEK_OF_MONTH - 1 != 0){
            Calendar.WEEK_OF_MONTH - 1
        }else{
            Calendar.WEEK_OF_MONTH
        }
}