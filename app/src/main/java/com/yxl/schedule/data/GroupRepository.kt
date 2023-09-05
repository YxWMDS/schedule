package com.yxl.schedule.data

import com.yxl.schedule.db.dao.GroupDAO
import com.yxl.schedule.db.entity.GroupEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepository @Inject constructor(
    private val groupDAO: GroupDAO
) {

    suspend fun getGroups(): List<GroupEntity>{
        return groupDAO.getAll()
    }

    suspend fun insert(group: GroupEntity){
        groupDAO.insert(group)
    }
}