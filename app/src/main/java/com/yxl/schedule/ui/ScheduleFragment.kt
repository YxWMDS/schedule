package com.yxl.schedule.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.schedule.ScheduleActivity
import com.yxl.schedule.adapters.ParentProfessorScheduleAdapter
import com.yxl.schedule.adapters.ParentStudentScheduleAdapter
import com.yxl.schedule.data.ScheduleRepository
import com.yxl.schedule.databinding.FragmentScheduleBinding
import com.yxl.schedule.model.ProfessorDayData
import com.yxl.schedule.model.StudentDayData


class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var parentStudentAdapter: ParentStudentScheduleAdapter
    private lateinit var parentProfessorAdapter: ParentProfessorScheduleAdapter
    private val viewModel: ScheduleViewModel by activityViewModels{ viewModelProvider }
    private val repository = ScheduleRepository()
    private val viewModelProvider = ScheduleViewModelProvider(repository)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater, container, false)
        Log.d("ViewModel", viewModel.toString())
        getGroups()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.fabSearchGroup.setOnClickListener {
            (activity as ScheduleActivity).openSearchDialog()
            setUpDialog()
        }
        Log.d("ViewModel", viewModel.studentSchedule.value.toString())
        getStudentSchedule()
        getProfessorSchedule()
    }

    private fun setUpStudentRecycler() {
        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun setUpRecycler() {
        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpToolbar() = with(binding){
        toolbar.toolbarBack.isVisible = false
        viewModel.weekNumber.observe(viewLifecycleOwner){
            toolbar.toolbarWeek.text = "$it Неделя"
        }
    }

    private fun getStudentSchedule() {
        setUpRecycler()

        viewModel.studentSchedule.observe(viewLifecycleOwner){
            parentStudentAdapter = ParentStudentScheduleAdapter(it)
            parentStudentAdapter.differ.submitList(it)
            binding.rvSchedule.adapter = parentStudentAdapter
        }

    }

    private fun getProfessorSchedule() {
        setUpRecycler()

        viewModel.professorSchedule.observe(viewLifecycleOwner){
            parentProfessorAdapter = ParentProfessorScheduleAdapter(it)
            parentProfessorAdapter.differ.submitList(it)
            binding.rvSchedule.adapter = parentProfessorAdapter
        }

    companion object {
//        private const val STUDENT_CODE = "student"
//        private const val PROFESSOR_CODE = "professor"
      
        @JvmStatic
        fun newInstance() =
            ScheduleFragment()
    }
}