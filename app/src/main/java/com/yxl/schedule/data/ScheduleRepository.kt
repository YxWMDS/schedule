package com.yxl.schedule.data

class ScheduleRepository {

    suspend fun getStudentSchedule(options: MutableMap<String, String>)
        = ScheduleApi().getStudentSchedule(options)

    suspend fun getProfessorSchedule(options: MutableMap<String, String>)
            = ScheduleApi().getProfessorSchedule(options)

    suspend fun getGroups()
            = ScheduleApi().getGroups()
}