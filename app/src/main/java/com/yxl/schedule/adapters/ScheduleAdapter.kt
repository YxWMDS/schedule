package com.yxl.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxl.schedule.databinding.ScheduleItemBinding
import com.yxl.schedule.models.ScheduleModel

class ScheduleAdapter(private val wholeSchedule: List<ScheduleModel>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(val binding: ScheduleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(scheduleModel: ScheduleModel) = with(binding){
            tvSubName.text = scheduleModel.subName
            tvStartTime.text = scheduleModel.startTime
            tvEndTime.text = scheduleModel.endTime
            tvProfName.text = scheduleModel.profName
            tvSubType.text = scheduleModel.subType
            tvSubRoom.text = scheduleModel.subRoom
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScheduleItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wholeSchedule.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(wholeSchedule[position])
    }
}