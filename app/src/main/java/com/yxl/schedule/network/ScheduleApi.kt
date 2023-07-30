package com.yxl.schedule.network

import com.yxl.schedule.consts.BackendUrls
import com.yxl.schedule.models.Data
import com.yxl.schedule.models.Schedule
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ScheduleApi {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("group")
     fun getSchedule(
        @QueryMap options: MutableMap<String, String>
    ): Call<Data>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("v1/schedule/{name}")
    suspend fun getProfessorSchedule(@Path("name") name: String): Response<List<Schedule>>

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