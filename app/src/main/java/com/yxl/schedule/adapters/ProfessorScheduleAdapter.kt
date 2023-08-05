package com.yxl.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yxl.schedule.databinding.ScheduleTeacherItemBinding
import com.yxl.schedule.model.TeacherData

class ProfessorScheduleAdapter : RecyclerView.Adapter<ProfessorScheduleAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ScheduleTeacherItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: TeacherData.Data.Schedule) = with(binding){
            tvSubName.text = schedule.subject.abbreviated
            tvGroupName.append(schedule.group.name)
            tvStartTime.text = schedule.time.start
            tvEndTime.text = schedule.time.start
            tvSubRoom.text = schedule.auditory
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScheduleTeacherItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallBack = object : DiffUtil.ItemCallback<TeacherData.Data.Schedule>(){
        override fun areItemsTheSame(oldItem: TeacherData.Data.Schedule, newItem: TeacherData.Data.Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TeacherData.Data.Schedule, newItem: TeacherData.Data.Schedule): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}