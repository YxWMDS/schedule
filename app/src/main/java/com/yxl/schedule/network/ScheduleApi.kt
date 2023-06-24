package com.yxl.schedule.network

import com.yxl.schedule.consts.BackendUrls
import com.yxl.schedule.models.ScheduleModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ScheduleApi {

    @GET("schedules")
    fun getSchedule(): Call<List<ScheduleModel>>

    companion object{

        fun get(): ScheduleApi {
           return Retrofit.Builder()
                .baseUrl(BackendUrls.scheduleBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ScheduleApi::class.java)
        }
    }

}