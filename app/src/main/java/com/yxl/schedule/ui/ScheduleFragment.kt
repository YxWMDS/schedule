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
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.schedule.adapters.StudentScheduleAdapter
import com.yxl.schedule.adapters.TeacherScheduleAdapter
import com.yxl.schedule.databinding.DialogSearchBinding
import com.yxl.schedule.databinding.FragmentScheduleBinding
import com.yxl.schedule.models.Groups
import com.yxl.schedule.models.ScheduleData
import com.yxl.schedule.network.ScheduleApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var studentScheduleAdapter: StudentScheduleAdapter
    private lateinit var teacherScheduleAdapter: TeacherScheduleAdapter
    private val groupsList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        setUpRecycler()
        getGroups()
        binding.fabSearchGroup.setOnClickListener {
            setUpDialog()
        }
    }

    private fun setUpRecycler() {
        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.toolbarBack.isVisible = false
    }

    private fun setUpDialog() {
        val list2 = listOf("1", "2")
        val spinnerGroupAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            groupsList
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

    private fun getGroups(){
        val groupsResponse = ScheduleApi().getGroups()
        groupsResponse.enqueue(object : Callback<Groups> {
            override fun onResponse(
                call: Call<Groups>,
                response: Response<Groups>
            ) {
                Log.d("ScheduleFragment", response.body().toString())
                for(i in response.body()?.data!!){
                    groupsList.add(i.name)
                }
            }

            override fun onFailure(call: Call<Groups>, t: Throwable) {
                Log.d("ScheduleFragment", t.message.toString())
            }

        })
    }

    private fun getStudentSchedule(group: String, subgroup: String) {
        val params = mutableMapOf<String, String>()
        params["group"] = group
        params["subgroup"] = subgroup
        params["weekdays[]"] = "1"

        val scheduleList = ScheduleApi().getSchedule(params)
        studentScheduleAdapter = StudentScheduleAdapter()
        scheduleList.enqueue(object : Callback<ScheduleData> {

            override fun onResponse(call: Call<ScheduleData>, response: Response<ScheduleData>) {
                studentScheduleAdapter.differ.submitList(response.body()?.data?.schedule)
                binding.rvSchedule.adapter = studentScheduleAdapter
            }

            override fun onFailure(call: Call<ScheduleData>, t: Throwable) {
                Log.d("ScheduleFragment", scheduleList.request().url().toString())
            }
        })

    }

    private fun getProfessorSchedule(name: String) {
        val params = mutableMapOf<String, String>()
        params.put("teacher", name)
        params.put("weekdays[]", "1")
        teacherScheduleAdapter = TeacherScheduleAdapter()
        CoroutineScope(Dispatchers.Main).launch{
            val professorSchedule = ScheduleApi().getProfessorSchedule(params)

            if(professorSchedule.isSuccessful){
                teacherScheduleAdapter.differ.submitList(professorSchedule.body()?.data?.schedule)
                binding.rvSchedule.adapter = teacherScheduleAdapter
            }else{
                Log.d("errorTAG", professorSchedule.raw().request().url().toString())
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ScheduleFragment()
    }
}