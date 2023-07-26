package com.yxl.schedule.models

data class Schedule(
    val auditory: Int,
    val building: String,
    val id: Int,
    val subgroup: Any,
    val subject: Subject,
    val teacher: Teacher,
    val time: Time,
    val type: Type,
    val weekday: Weekday,
    val weeks: String
)

data class Subject(
    val abbreviated: String,
    val full: String
)

data class Teacher(
    val department: Any,
    val fullName: String,
    val id: Int,
    val position: Any
)

data class Time(
    val end: String,
    val number: Int,
    val start: String
)

data class Type(
    val abbreviated: String,
    val full: String
)

data class Weekday(
    val name: String,
    val number: Int
)