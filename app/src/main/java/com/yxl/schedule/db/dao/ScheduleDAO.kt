package com.yxl.schedule.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.yxl.schedule.db.entity.GroupEntity
import com.yxl.schedule.db.entity.ScheduleEntity

@Dao
interface ScheduleDAO {

    @Query("SELECT * FROM ${ScheduleEntity.TABLE_NAME} " +
            "WHERE ${ScheduleEntity.COLUMN_GROUP} = :groupp " +
            "AND ${ScheduleEntity.COLUMN_SUBGROUP} = :subgr " +
            "AND ${ScheduleEntity.COLUMN_DAY} = :day " +
            "AND ${ScheduleEntity.COLUMN_WEEK} = :week")
    suspend fun getAll(groupp: String, subgr: String, day: String, week: String): List<ScheduleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: ScheduleEntity)
}