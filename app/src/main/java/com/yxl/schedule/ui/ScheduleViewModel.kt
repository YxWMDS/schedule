package com.yxl.schedule.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxl.schedule.data.GroupRepository
import com.yxl.schedule.data.ScheduleRepository
import com.yxl.schedule.db.entity.GroupEntity
import com.yxl.schedule.db.entity.ScheduleEntity
import com.yxl.schedule.data.model.ProfessorDayData
import com.yxl.schedule.data.model.StudentDayData
import com.yxl.schedule.prefs.SchedulePreferences
import com.yxl.schedule.utils.Constants
import com.yxl.schedule.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val preferences: SchedulePreferences,
    private val groupRepository: GroupRepository
): ViewModel() {

    val studentSchedule: MutableLiveData<List<StudentDayData>> = MutableLiveData()
    val professorSchedule: MutableLiveData<List<ProfessorDayData>> = MutableLiveData()
    val groups: MutableLiveData<List<String>> = MutableLiveData()
    var weekNumber = MutableLiveData(DateUtils.weekOfMonth)
    val isLoading = MutableLiveData(false)
    val isGroupsLoaded = MutableLiveData(false)
    init {
        getGroups()
        tryGetStSchedule()
    }

    private fun getGroups() = viewModelScope.launch {
        val response = scheduleRepository.getGroups().body()
        val list = mutableListOf<String>()
        for (i in response?.data!!) {
            list.add(i.name)
            groupRepository.insert(GroupEntity(i.name))
        }
        groups.postValue(list)
        isGroupsLoaded.postValue(true)
    }

    private fun getGroupsFromDb() = viewModelScope.launch {
        val res = mutableListOf<String>()
        if(groupRepository.getGroups().isNotEmpty()){
            val list = groupRepository.getGroups()
            for(i in list){
                res.add(i.name)
            }
        }
        groups.postValue(res)
    }

//    fun setWeekNumber(week: Int) = viewModelScope.launch{
//        weekNumber.emit(week)
//    }

    fun getStudentScheduleWeek(group: String, subgroup: String) = viewModelScope.launch{
        isLoading.value = true
        val list = mutableListOf<StudentDayData>()
        for(i in Constants.dayNames.indices){
            list.add(StudentDayData(Constants.dayNames[i], scheduleRepository.getStudentSchedule(group, subgroup, "${i+1}", weekNumber.value.toString()).body()?.data?.schedule))
        }

        studentSchedule.postValue(list)
        preferences.setGroup(group)
        preferences.setSubgroup(subgroup)
        preferences.setWeekNumber(weekNumber.toString())
        isLoading.value = false
    }

    private fun addToDb(schedule: ScheduleEntity) = viewModelScope.launch {
        scheduleRepository.insertScheduleDb(schedule)
    }

    fun getProfessorScheduleWeek(teacher: String?) = viewModelScope.launch {
        val list = mutableListOf<ProfessorDayData>()
        if(!teacher.isNullOrEmpty()){
            for(i in Constants.dayNames.indices){
                list.add(ProfessorDayData(Constants.dayNames[i], scheduleRepository.getProfessorSchedule(teacher, "${i+1}").body()?.data?.schedule))
            }
        }
        professorSchedule.postValue(list)
    }

    private fun tryGetStSchedule() = viewModelScope.launch{
        if(preferences.getGroup.firstOrNull() != null && preferences.getSubgroup.firstOrNull() != null){
            getStudentScheduleWeek(
                preferences.getGroup.first()!!,
                preferences.getSubgroup.first()!!
            )
        }
    }

    fun onWeekChanged() = viewModelScope.launch{
        getStudentScheduleWeek(
            preferences.getGroup.first()!!,
            preferences.getSubgroup.first()!!
        )
    }



}