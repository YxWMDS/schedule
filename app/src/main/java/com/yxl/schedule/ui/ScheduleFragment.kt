package com.yxl.schedule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.schedule.adapters.ScheduleAdapter
import com.yxl.schedule.databinding.FragmentScheduleBinding
import com.yxl.schedule.models.ScheduleModel
import com.yxl.schedule.network.ScheduleApi
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
        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSchedule()
    }

    private fun getSchedule(){
        val scheduleList = ScheduleApi.get().getSchedule()
        scheduleList.enqueue(object : Callback<List<ScheduleModel>>{
            override fun onResponse(
                call: Call<List<ScheduleModel>>,
                response: Response<List<ScheduleModel>>
            ) {
                scheduleAdapter = ScheduleAdapter(response.body()!!)
                binding.rvSchedule.adapter = scheduleAdapter
            }

            override fun onFailure(call: Call<List<ScheduleModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ScheduleFragment().apply {

            }
    }
}