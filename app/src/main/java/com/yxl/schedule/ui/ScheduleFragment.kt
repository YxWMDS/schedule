package com.yxl.schedule.ui

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.schedule.adapters.StudentScheduleAdapter
import com.yxl.schedule.adapters.TeacherScheduleAdapter
import com.yxl.schedule.data.ScheduleRepository
import com.yxl.schedule.databinding.DialogSearchBinding
import com.yxl.schedule.databinding.FragmentScheduleBinding


class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var studentScheduleAdapter: StudentScheduleAdapter
    private lateinit var teacherScheduleAdapter: TeacherScheduleAdapter
    private val viewModel: ScheduleViewModel by activityViewModels{ viewModelProvider }
    private val repository = ScheduleRepository()
    private val viewModelProvider = ScheduleViewModelProvider(repository)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater, container, false)
        Log.d("ViewModel", viewModel.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())

        getGroups()
        binding.fabSearchGroup.setOnClickListener {
            setUpDialog()
        }
    }

    private fun setUpStudentRecycler() {
        binding.apply {
            studentScheduleAdapter = StudentScheduleAdapter()
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())
            rvSchedule.adapter = studentScheduleAdapter
        }
    }

    private fun setUpProfessorRecycler() {
        binding.apply {
            teacherScheduleAdapter = TeacherScheduleAdapter()
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())
            rvSchedule.adapter = teacherScheduleAdapter
        }
    }

    private fun setUpToolbar() = with(binding){
        toolbar.toolbarBack.isVisible = false
        viewModel.weekNumber.observe(viewLifecycleOwner){
            toolbar.toolbarWeek.text = it
        }
    }

    private fun setUpDialog() {
        val list2 = listOf("1", "2")
        val spinnerGroupAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            getGroups()
        )
        val spinnerSubgroupAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            list2
        )
        val dialogBinding = DialogSearchBinding.inflate(LayoutInflater.from(layoutInflater.context))

        val searchDialog = AlertDialog.Builder(context)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.apply {
            spinnerGroups.adapter = spinnerGroupAdapter
            spinnerSubgroups.adapter = spinnerSubgroupAdapter

            etProf.isEnabled = sProfessor.isChecked
            sProfessor.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    etProf.isEnabled = true
                    spinnerGroups.isEnabled = false
                    spinnerSubgroups.isEnabled = false
                } else {
                    etProf.isEnabled = false
                    spinnerGroups.isEnabled = true
                    spinnerSubgroups.isEnabled = true
                }
            }

            bConfirm.setOnClickListener {
                if (sProfessor.isChecked) {
                    getProfessorSchedule(etProf.text.toString())
                } else {
                    getStudentSchedule(
                        spinnerGroups.selectedItem.toString(),
                        spinnerSubgroups.selectedItem.toString()
                    )
                }
                searchDialog.cancel()
            }

            searchDialog.show()
        }
    }

    private fun getGroups(): MutableList<String>{
        val groupsList = mutableListOf<String>()
        viewModel.groups.observe(viewLifecycleOwner) {
            for (i in it.data) {
                groupsList.add(i.name)
            }
        }
        return groupsList
    }

    private fun getStudentSchedule(group: String, subgroup: String) {
        setUpStudentRecycler()
        val params = mutableMapOf<String, String>()
        params["group"] = group
        params["subgroup"] = subgroup
        params["weekdays[]"] = "1"
        viewModel.getStudentSchedule(params)
        viewModel.studentSchedule.observe(viewLifecycleOwner) {
            studentScheduleAdapter.differ.submitList(it.data.schedule)
        }

    }

    private fun getProfessorSchedule(name: String) {
        setUpProfessorRecycler()
        val params = mutableMapOf<String, String>()
        params["teacher"] = name
        params["weekdays[]"] = "1"
        viewModel.getProfessorSchedule(params)
        viewModel.professorSchedule.observe(viewLifecycleOwner){
            teacherScheduleAdapter.differ.submitList(it.data.schedule)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ScheduleFragment()
    }
}