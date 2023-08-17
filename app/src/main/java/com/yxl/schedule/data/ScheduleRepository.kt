package com.yxl.schedule.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepository @Inject constructor() {

    suspend fun getStudentSchedule(group: String, subgroup: String, weekdays: String)
        = ScheduleApi().getStudentSchedule(group, subgroup, weekdays)

    suspend fun getProfessorSchedule(teacher: String, weekdays: String)
            = ScheduleApi().getProfessorSchedule(teacher, weekdays)

    suspend fun getGroups()
            = ScheduleApi().getGroups()
}