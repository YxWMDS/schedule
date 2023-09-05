package com.yxl.schedule.data

import com.yxl.schedule.db.dao.ScheduleDAO
import com.yxl.schedule.db.entity.ScheduleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepository @Inject constructor(
    private val scheduleDAO: ScheduleDAO
) {

    suspend fun getStudentSchedule(group: String, subgroup: String, weekdays: String, weeks: String)
        = ScheduleApi().getStudentSchedule(group, subgroup, weekdays, weeks)

    suspend fun getProfessorSchedule(teacher: String, weekdays: String)
            = ScheduleApi().getProfessorSchedule(teacher, weekdays)

    suspend fun getGroups()
            = ScheduleApi().getGroups()

    suspend fun getScheduleDb(group: String, subgroup: String, day: String, week: String)
        = scheduleDAO.getAll(group, subgroup, day, week)

    suspend fun insertScheduleDb(schedule: ScheduleEntity) = scheduleDAO.insert(schedule)
}