package com.yxl.schedule.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.schedule.adapters.ParentProfessorScheduleAdapter
import com.yxl.schedule.adapters.ParentStudentScheduleAdapter
import com.yxl.schedule.data.ScheduleApi
import com.yxl.schedule.data.ScheduleRepository
import com.yxl.schedule.databinding.DialogSearchBinding
import com.yxl.schedule.databinding.FragmentScheduleBinding
import com.yxl.schedule.model.ProfessorDayData
import com.yxl.schedule.model.ScheduleData
import com.yxl.schedule.model.StudentDayData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


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
            setUpDialog()
        }
    }

    private fun setUpStudentRecycler() {
        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun setUpProfessorRecycler() {
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

    @Suppress("UNCHECKED_CAST")
    private fun getStudentSchedule(group: String, subgroup: String) {
        setUpStudentRecycler()
        val dayNames = listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")
        val days = mutableListOf<StudentDayData>()
        CoroutineScope(Dispatchers.Main).launch {
            for(i in 0..5){
                val day = ScheduleApi().getStudentSchedule(group, subgroup,"${i+1}").body()
                Log.d("listttt", day.toString())
                days.add(StudentDayData(dayNames[i], day?.data?.schedule))
            }
//            val daysList = getWeekSchedule(STUDENT_CODE, group, subgroup) as List<StudentDayData>
            parentStudentAdapter = ParentStudentScheduleAdapter(days)
            parentStudentAdapter.differ.submitList(days)
            binding.rvSchedule.adapter = parentStudentAdapter
        }




    }

    @Suppress("UNCHECKED_CAST")
    private fun getProfessorSchedule(teacher: String) {
        setUpProfessorRecycler()
        val daysList = getWeekSchedule(code = PROFESSOR_CODE, teacher = teacher) as List<ProfessorDayData>
        parentProfessorAdapter = ParentProfessorScheduleAdapter(daysList)
        parentProfessorAdapter.differ.submitList(daysList)
        binding.rvSchedule.adapter = parentProfessorAdapter

    }

    private fun getWeekSchedule(
        code: String, group: String = "", subgroup: String = "", teacher: String = ""
    ): List<Any>{
        val days = listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")
        val daysList = mutableListOf<Any>()

        when (code) {
            STUDENT_CODE -> {
                for(i in days.indices){
                    viewModel.getStudentSchedule(group, subgroup, "${i+1}")

                    viewModel.studentSchedule.observe(viewLifecycleOwner){
                        Log.d("list", it.data.schedule.toString())
                        daysList.add(StudentDayData(days[i], it.data.schedule))
                    }
                }
            }
            PROFESSOR_CODE -> {
                for(i in days.indices){
                    viewModel.getProfessorSchedule(teacher, "${i+1}")
                    viewModel.professorSchedule.observe(viewLifecycleOwner){
                        daysList.add(ProfessorDayData(days[i], it.data.schedule))
                    }
                }
            }
        }
        Log.d("list", daysList.toString())
        return daysList
    }

    companion object {
        private const val STUDENT_CODE = "student"
        private const val PROFESSOR_CODE = "professor"
        @JvmStatic
        fun newInstance() =
            ScheduleFragment()
    }
}