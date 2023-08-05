package com.yxl.schedule.model


import com.google.gson.annotations.SerializedName

data class ScheduleData(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("schedule")
        val schedule: List<Schedule>? = null,
        @SerializedName("group")
        val group: Group
    ) {
        data class Schedule(
            @SerializedName("id")
            val id: Int,
            @SerializedName("subject")
            val subject: Subject,
            @SerializedName("type")
            val type: Type,
            @SerializedName("auditory")
            val auditory: Int,
            @SerializedName("building")
            val building: String,
            @SerializedName("weeks")
            val weeks: String,
            @SerializedName("subgroup")
            val subgroup: Int,
            @SerializedName("time")
            val time: Time,
            @SerializedName("weekday")
            val weekday: Weekday,
            @SerializedName("teacher")
            val teacher: Teacher
        ) {
            data class Subject(
                @SerializedName("abbreviated")
                val abbreviated: String,
                @SerializedName("full")
                val full: String
            )

            data class Type(
                @SerializedName("abbreviated")
                val abbreviated: String,
                @SerializedName("full")
                val full: String
            )

            data class Time(
                @SerializedName("number")
                val number: Int,
                @SerializedName("start")
                val start: String,
                @SerializedName("end")
                val end: String
            )

            data class Weekday(
                @SerializedName("number")
                val number: Int,
                @SerializedName("name")
                val name: String
            )

            data class Teacher(
                @SerializedName("id")
                val id: Int,
                @SerializedName("fullName")
                val fullName: String,
                @SerializedName("department")
                val department: Any,
                @SerializedName("position")
                val position: Any
            )
        }

        data class Group(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: Type
        ) {
            data class Type(
                @SerializedName("names")
                val names: Names,
                @SerializedName("timeType")
                val timeType: String
            ) {
                data class Names(
                    @SerializedName("abbreviated")
                    val abbreviated: String,
                    @SerializedName("full")
                    val full: String
                )
            }
        }
    }
}

data class Groups(
    @SerializedName("data")
    val data: List<ScheduleData.Data.Group>
)
