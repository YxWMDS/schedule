package com.yxl.schedule.model

data class StudentDayData(
    val day: String? = null,
    val schedule: List<ScheduleData.Data.Schedule>? = null
)