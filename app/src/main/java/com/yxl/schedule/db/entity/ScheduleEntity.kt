package com.yxl.schedule.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yxl.schedule.db.entity.ScheduleEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class ScheduleEntity(
    @ColumnInfo(name = COLUMN_NAME) val scheduleName: String,
    @ColumnInfo(name = COLUMN_TEACHER) val teacher: String,
    @ColumnInfo(name = COLUMN_START) val start: String,
    @ColumnInfo(name = COLUMN_END) val end: String,
    @ColumnInfo(name = COLUMN_TYPE) val type: String,
    @ColumnInfo(name = COLUMN_WEEK) val week: String,
    @ColumnInfo(name = COLUMN_DAY) val day: String,
    @ColumnInfo(name = COLUMN_AUDITORY) val auditory: String,
    @ColumnInfo(name = COLUMN_GROUP) val scheduleGroup: String,
    @ColumnInfo(name = COLUMN_SUBGROUP) val scheduleSubgroup: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0

    companion object {
        const val TABLE_NAME = "SCHEDULES"
        const val COLUMN_NAME = "name"
        const val COLUMN_TEACHER = "teacher"
        const val COLUMN_START = "start"
        const val COLUMN_END = "end"
        const val COLUMN_TYPE = "type"
        const val COLUMN_WEEK = "week"
        const val COLUMN_DAY = "day"
        const val COLUMN_AUDITORY = "auditory"
        const val COLUMN_GROUP = "groupp"
        const val COLUMN_SUBGROUP = "subgroupp"
        const val COLUMN_ID = "id"
    }
}