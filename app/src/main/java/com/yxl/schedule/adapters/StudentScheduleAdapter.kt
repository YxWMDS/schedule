package com.yxl.schedule.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yxl.schedule.R
import com.yxl.schedule.databinding.ScheduleItemBinding
import com.yxl.schedule.data.model.ScheduleData
import com.yxl.schedule.utils.Additions

class StudentScheduleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ScheduleViewHolder(private val binding: ScheduleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val colorView = binding.vColorType
        fun bind(schedule: ScheduleData.Data.Schedule) = with(binding){
            tvSubName.text = schedule.subject.abbreviated
            tvSubType.text = schedule.type.abbreviated
            tvProfName.text = schedule.teacher.fullName
            tvStartTime.text = schedule.time.start
            tvEndTime.text = schedule.time.end
            tvSubRoom.text = schedule.auditory.toString()
        }
    }

    class NoScheduleHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val noScheduleView = LayoutInflater.from(parent.context)
            .inflate(R.layout.no_schedule_item, parent, false)
        val binding = ScheduleItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return if(differ.currentList.isEmpty()){
            NoScheduleHolder(noScheduleView)
        }else{
            ScheduleViewHolder(binding)
        }
    }

    override fun getItemCount() = if(differ.currentList.size > 0) differ.currentList.size else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ScheduleViewHolder){
            holder.bind(differ.currentList[position])
            Additions.changeBackground(
                differ.currentList[position].type.abbreviated,
                holder.colorView
            )
        }

    }

    private val differCallBack = object : DiffUtil.ItemCallback<ScheduleData.Data.Schedule>(){
        override fun areItemsTheSame(oldItem: ScheduleData.Data.Schedule, newItem: ScheduleData.Data.Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScheduleData.Data.Schedule, newItem: ScheduleData.Data.Schedule): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}