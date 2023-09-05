package com.yxl.schedule.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yxl.schedule.db.entity.GroupEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class GroupEntity(
    @ColumnInfo(name = COLUMN_NAME) val name: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0

    companion object {
        const val TABLE_NAME = "GROUPS"
        const val COLUMN_NAME = "name"
        const val COLUMN_ID = "id"
    }
}