package com.yxl.schedule.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.yxl.schedule.db.entity.GroupEntity
import com.yxl.schedule.db.entity.ScheduleEntity

@Dao
interface GroupDAO {

    @Transaction
    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    suspend fun getAll(): List<GroupEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: GroupEntity)

}