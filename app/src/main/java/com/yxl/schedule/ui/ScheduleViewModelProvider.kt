package com.yxl.schedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxl.schedule.data.ScheduleRepository

class ScheduleViewModelProvider(private val repository: ScheduleRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ScheduleViewModel(repository) as T
    }
}