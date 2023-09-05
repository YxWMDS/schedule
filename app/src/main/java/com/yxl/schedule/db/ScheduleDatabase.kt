package com.yxl.schedule.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yxl.schedule.db.dao.GroupDAO
import com.yxl.schedule.db.dao.ScheduleDAO
import com.yxl.schedule.db.entity.GroupEntity
import com.yxl.schedule.db.entity.ScheduleEntity

@Database(entities = [GroupEntity::class, ScheduleEntity::class], version = 1)
abstract class ScheduleDatabase : RoomDatabase(){

    abstract fun groupsDAO(): GroupDAO
    abstract fun scheduleDAO(): ScheduleDAO

    companion object{
        const val DATABASE_NAME = "schedule_db"
    }
}