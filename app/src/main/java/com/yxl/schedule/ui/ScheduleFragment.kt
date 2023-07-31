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
import com.yxl.schedule.adapters.ScheduleAdapter
import com.yxl.schedule.databinding.DialogSearchBinding
import com.yxl.schedule.databinding.FragmentScheduleBinding
import com.yxl.schedule.models.ScheduleData
import com.yxl.schedule.network.ScheduleApi
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var scheduleAdapter: ScheduleAdapter

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

    private fun onSearchGroupClick() {
        val group: String?
        val subgroup: String?

    }

    private fun setUpDialog() {
        val list = listOf("1", "ИП291", "3", "4")
        val list2 = listOf("1", "2")
        val spinnerGroupAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            list
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
                    etProf.isClickable = true
                    spinnerGroups.isClickable = false
                    spinnerSubgroups.isClickable = false
                } else {
                    etProf.isClickable = false
                    spinnerGroups.isClickable = true
                    spinnerSubgroups.isClickable = true
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

    @OptIn(DelicateCoroutinesApi::class)
    private fun getStudentSchedule(group: String, subgroup: String) {
        val params = mutableMapOf<String, String>()
        params["group"] = group
        params["subgroup"] = subgroup
        params["weekdays[]"] = "1"

        val scheduleList = ScheduleApi().getSchedule(params)
        scheduleAdapter = ScheduleAdapter()
        scheduleList.enqueue(object : Callback<ScheduleData> {

            override fun onResponse(call: Call<ScheduleData>, response: Response<ScheduleData>) {
                scheduleAdapter.differ.submitList(response.body()?.data?.schedule)
                binding.rvSchedule.adapter = scheduleAdapter
                Log.d("successFragment", "success")
                Log.d("successFragment", scheduleList.request().url().toString())
                Log.d("successFragment", response.body()?.data?.group?.name.toString())

            }

            override fun onFailure(call: Call<ScheduleData>, t: Throwable) {
                Log.d("ScheduleFragment", scheduleList.request().url().toString())
            }
        })

    }

    private fun getProfessorSchedule(name: String) {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ScheduleFragment()
    }
}