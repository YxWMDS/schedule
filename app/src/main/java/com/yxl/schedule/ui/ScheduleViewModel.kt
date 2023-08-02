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
    val weekNumber = MutableLiveData<String>()

    init {
        getGroups()
        weekNumber.value = "1 неделя"
    }

    fun getStudentSchedule(options: MutableMap<String, String>) = viewModelScope.launch {
        val response = repository.getStudentSchedule(options)
        studentSchedule.postValue(response.body())
    }

    fun getProfessorSchedule(options: MutableMap<String, String>) = viewModelScope.launch {
        professorSchedule.postValue(repository.getProfessorSchedule(options).body())
    }

    private fun getGroups() = viewModelScope.launch {
        val response = repository.getGroups()
        groups.postValue(response.body())
    }
}