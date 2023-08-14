package com.yxl.schedule.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxl.schedule.data.ScheduleRepository
import com.yxl.schedule.model.ProfessorDayData
import com.yxl.schedule.model.StudentDayData
import com.yxl.schedule.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val repository: ScheduleRepository
): ViewModel() {

    val studentSchedule: MutableLiveData<List<StudentDayData>> = MutableLiveData()
    val professorSchedule: MutableLiveData<List<ProfessorDayData>> = MutableLiveData()
    val groups: MutableLiveData<List<String>> = MutableLiveData()
    val weekNumber = MutableLiveData<Int>()

    init {
        getGroups()
        weekNumber.value = Constants.weekOfMonth
    }

    private fun getGroups() = viewModelScope.launch {
        val response = repository.getGroups().body()
        val list = mutableListOf<String>()
        for(i in response?.data!!){
            list.add(i.name)
        }
        groups.postValue(list)
    }

    fun getStudentScheduleWeek(group: String, subgroup: String) = viewModelScope.launch{
        val list = mutableListOf<StudentDayData>()
        for(i in Constants.dayNames.indices){
            list.add(StudentDayData(Constants.dayNames[i], repository.getStudentSchedule(group, subgroup, "${i+1}").body()?.data?.schedule))
        }
        studentSchedule.postValue(list)
        Log.d("LISTVIEWMODEL", list.toString())
        weekNumber.value = 1
    }

    fun getProfessorScheduleWeek(teacher: String) = viewModelScope.launch {
        val list = mutableListOf<ProfessorDayData>()
        for(i in Constants.dayNames.indices){
            list.add(ProfessorDayData(Constants.dayNames[i], repository.getProfessorSchedule(teacher, "${i+1}").body()?.data?.schedule))
        }
        professorSchedule.postValue(list)
        Log.d("LISTVIEWMODEL", list.toString())
    }

}