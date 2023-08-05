package com.yxl.schedule.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxl.schedule.data.ScheduleRepository
import com.yxl.schedule.model.Groups
import com.yxl.schedule.model.ScheduleData
import com.yxl.schedule.model.TeacherData
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val repository: ScheduleRepository
): ViewModel() {

    val studentSchedule: MutableLiveData<ScheduleData> = MutableLiveData()
    val professorSchedule: MutableLiveData<TeacherData> = MutableLiveData()
    val groups: MutableLiveData<Groups> = MutableLiveData()
    val weekNumber = MutableLiveData<Int>()

    init {
        getGroups()
        weekNumber.value = 1
    }

    fun getStudentSchedule(group: String, subgroup: String, weekdays: String) = viewModelScope.launch {
        val response = repository.getStudentSchedule(group, subgroup, weekdays)
        studentSchedule.postValue(response.body())
    }

    fun getProfessorSchedule(teacher: String, weekdays: String) = viewModelScope.launch {
        professorSchedule.postValue(repository.getProfessorSchedule(teacher, weekdays).body())
    }

    private fun getGroups() = viewModelScope.launch {
        val response = repository.getGroups()
        groups.postValue(response.body())
    }
}