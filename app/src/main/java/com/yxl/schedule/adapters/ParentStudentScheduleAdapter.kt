package com.yxl.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxl.schedule.databinding.ScheduleStudentDayItemBinding
import com.yxl.schedule.model.StudentDayData

class ParentStudentScheduleAdapter(private val scheduleList: List<StudentDayData>):
    RecyclerView.Adapter<ParentStudentScheduleAdapter.ViewHolder>() {

    class ViewHolder(val binding: ScheduleStudentDayItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: StudentDayData) = with(binding){
            tvDay.text = day.day
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScheduleStudentDayItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = 6

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.binding.rvSubjects.layoutManager =
             LinearLayoutManager(holder.itemView.context)
        val studentAdapter = StudentScheduleAdapter()
        studentAdapter.differ.submitList(scheduleList[position].schedule)
        holder.binding.rvSubjects.adapter = studentAdapter
    }

    private val differCallBack = object : DiffUtil.ItemCallback<StudentDayData>(){
        override fun areItemsTheSame(oldItem: StudentDayData, newItem: StudentDayData): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: StudentDayData, newItem: StudentDayData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}