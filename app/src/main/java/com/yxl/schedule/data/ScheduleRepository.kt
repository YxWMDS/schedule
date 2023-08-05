package com.yxl.schedule.data

class ScheduleRepository {

    suspend fun getStudentSchedule(group: String, subgroup: String, weekdays: String)
        = ScheduleApi().getStudentSchedule(group, subgroup, weekdays)

    suspend fun getProfessorSchedule(teacher: String, weekdays: String)
            = ScheduleApi().getProfessorSchedule(teacher, weekdays)

    suspend fun getGroups()
            = ScheduleApi().getGroups()
}