package com.yxl.schedule.utils

import android.view.View
import com.yxl.schedule.R

object Additions {

    fun changeBackground(type: String, view: View){
        when(type){
            "ЛК" ->{
                view.setBackgroundResource(R.color.light_green)
            }
            "ПЗ" ->{
                view.setBackgroundResource(R.color.light_blue)
            }
            "ЛР" ->{
                view.setBackgroundResource(R.color.light_red)
            }
        }
    }
}