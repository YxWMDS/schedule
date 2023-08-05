package com.yxl.schedule.data

import com.yxl.schedule.consts.BackendUrls
import com.yxl.schedule.model.Groups
import com.yxl.schedule.model.ScheduleData
import com.yxl.schedule.model.TeacherData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ScheduleApi {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("group")
    suspend fun getStudentSchedule(
        @Query("group") group: String,
        @Query("subgroup") subgroup: String,
        @Query("weekdays[]") weekdays: String
    ): Response<ScheduleData>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("teacher")
    suspend fun getProfessorSchedule(
        @Query("teacher") teacher: String,
        @Query("weekdays[]") weekdays: String
    ): Response<TeacherData>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("group/list")
    suspend fun getGroups(): Response<Groups>

    companion object{

         operator fun invoke(): ScheduleApi {
           return Retrofit.Builder()
                .baseUrl(BackendUrls.scheduleBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ScheduleApi::class.java)
        }
    }

}