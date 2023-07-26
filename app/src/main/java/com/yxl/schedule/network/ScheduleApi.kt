package com.yxl.schedule.network

import com.yxl.schedule.consts.BackendUrls
import com.yxl.schedule.models.Schedule
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ScheduleApi {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("v1/schedule/{group}")
    suspend fun getSchedule(@Path("group") group: String): Response<List<Schedule>>

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