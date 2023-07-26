package com.yxl.schedule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxl.schedule.adapters.ScheduleAdapter
import com.yxl.schedule.databinding.FragmentScheduleBinding
import com.yxl.schedule.models.Schedule
import com.yxl.schedule.network.ScheduleApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
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
        setUpRecycler()
        binding.fabSearchGroup.setOnClickListener {
            onSearchGroupClick()
        }
    }

    private fun setUpRecycler(){
        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onSearchGroupClick(){
        val group = ""
        getSchedule(group)
    }

    private fun getSchedule(group: String){
        //val scheduleList = ScheduleApi.get().getSchedule("")

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ScheduleFragment()
    }
}