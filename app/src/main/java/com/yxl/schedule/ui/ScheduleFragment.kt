package com.yxl.schedule.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.schedule.adapters.ParentProfessorScheduleAdapter
import com.yxl.schedule.adapters.ParentStudentScheduleAdapter
import com.yxl.schedule.databinding.FragmentScheduleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var parentStudentAdapter: ParentStudentScheduleAdapter
    private lateinit var parentProfessorAdapter: ParentProfessorScheduleAdapter
    private val viewModel: ScheduleViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater, container, false)
        Log.d("ViewModel", viewModel.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        Log.d("ViewModel", viewModel.studentSchedule.value.toString())
        getStudentSchedule()
        getProfessorSchedule()
    }

    private fun setUpRecycler() {
        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getStudentSchedule() {
        setUpRecycler()
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.rvSchedule.isVisible = !it

            when {
                it -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        viewModel.studentSchedule.observe(viewLifecycleOwner){
            parentStudentAdapter = ParentStudentScheduleAdapter(it)
            parentStudentAdapter.differ.submitList(it)
            binding.rvSchedule.adapter = parentStudentAdapter
        }

    }

    private fun getProfessorSchedule() {
        setUpRecycler()

        viewModel.professorSchedule.observe(viewLifecycleOwner) {
            parentProfessorAdapter = ParentProfessorScheduleAdapter(it)
            parentProfessorAdapter.differ.submitList(it)
            binding.rvSchedule.adapter = parentProfessorAdapter
        }
    }
    companion object {
//        private const val STUDENT_CODE = "student"
//        private const val PROFESSOR_CODE = "professor"
      
        @JvmStatic
        fun newInstance() =
            ScheduleFragment()
    }
}
