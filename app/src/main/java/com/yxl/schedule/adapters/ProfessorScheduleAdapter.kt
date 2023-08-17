package com.yxl.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yxl.schedule.databinding.ScheduleProfessorItemBinding
import com.yxl.schedule.model.ProfessorData
import com.yxl.schedule.utils.Additions

class ProfessorScheduleAdapter : RecyclerView.Adapter<ProfessorScheduleAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ScheduleProfessorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val colorView = binding.vColorType
        fun bind(schedule: ProfessorData.Data.Schedule) = with(binding){
            tvSubName.text = schedule.subject.abbreviated
            tvGroupName.append(schedule.group.name)
            tvStartTime.text = schedule.time.start
            tvEndTime.text = schedule.time.start
            tvSubRoom.text = schedule.auditory
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScheduleProfessorItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        Additions.changeBackground(
            differ.currentList[position].type.abbreviated,
            holder.colorView
        )
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallBack = object : DiffUtil.ItemCallback<ProfessorData.Data.Schedule>(){
        override fun areItemsTheSame(oldItem: ProfessorData.Data.Schedule, newItem: ProfessorData.Data.Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProfessorData.Data.Schedule, newItem: ProfessorData.Data.Schedule): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}