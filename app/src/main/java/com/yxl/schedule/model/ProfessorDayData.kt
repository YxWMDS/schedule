package com.yxl.schedule.model

data class ProfessorDayData(
    val day: String? = null,
    val schedule: List<TeacherData.Data.Schedule>? = null
)
