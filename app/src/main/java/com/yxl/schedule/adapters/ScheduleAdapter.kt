package com.yxl.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxl.schedule.databinding.ScheduleItemBinding
import com.yxl.schedule.models.Schedule

class ScheduleAdapter(private val wholeSchedule: List<Schedule>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(val binding: ScheduleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: Schedule) = with(binding){

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