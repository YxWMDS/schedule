package com.yxl.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxl.schedule.databinding.ScheduleProfessorDayItemBinding
import com.yxl.schedule.data.model.ProfessorDayData

class ParentProfessorScheduleAdapter(private val scheduleList: List<ProfessorDayData>) : RecyclerView.Adapter<ParentProfessorScheduleAdapter.ViewHolder>() {

    class ViewHolder(val binding: ScheduleProfessorDayItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: ProfessorDayData) = with(binding){
            tvDay.text = day.day
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScheduleProfessorDayItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = 6

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.binding.rvSubjects.layoutManager =
            LinearLayoutManager(holder.itemView.context)
        val professorAdapter = ProfessorScheduleAdapter()
        professorAdapter.differ.submitList(scheduleList[position].schedule)
        holder.binding.rvSubjects.adapter = professorAdapter
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ProfessorDayData>(){
        override fun areItemsTheSame(oldItem: ProfessorDayData, newItem: ProfessorDayData): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: ProfessorDayData, newItem: ProfessorDayData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}