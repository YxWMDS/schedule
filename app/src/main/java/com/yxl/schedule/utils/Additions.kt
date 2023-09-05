package com.yxl.schedule.utils

import android.view.View
import com.yxl.schedule.R
import com.yxl.schedule.data.model.ScheduleData
import com.yxl.schedule.db.entity.ScheduleEntity

object Additions {

    fun changeBackground(type: String, view: View) {
        when (type) {
            "ЛК" -> {
                view.setBackgroundResource(R.color.light_green)
            }

            "ПЗ" -> {
                view.setBackgroundResource(R.color.light_blue)
            }

            "ЛР" -> {
                view.setBackgroundResource(R.color.light_red)
            }
            else -> {
                view.setBackgroundResource(R.color.black)
            }
        }
    }

    fun convertToEntity(list: MutableList<ScheduleData.Data.Schedule>, group: String): List<ScheduleEntity>{
        val rList = mutableListOf<ScheduleEntity>()
        for(i in list){
            rList.add(
                ScheduleEntity(
                i.subject.abbreviated,
                i.teacher.fullName,
                i.time.start.toString(),
                i.time.end,
                i.type.abbreviated,
                i.weeks,
                i.weekday.name,
                i.auditory.toString(),
                group,
                i.subgroup.toString()
            )
            )
        }
        return rList
    }
}