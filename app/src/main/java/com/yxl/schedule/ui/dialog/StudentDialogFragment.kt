package com.yxl.schedule.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yxl.schedule.databinding.DialogStudentBinding
import com.yxl.schedule.ui.ScheduleViewModel
import com.yxl.schedule.utils.Constants

class StudentDialogFragment : Fragment() {

    private lateinit var binding: DialogStudentBinding
    private val viewModel: ScheduleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val spinnerGroupAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            viewModel.groups.value as MutableList<String>
        )

        val spinnerSubgroupAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            Constants.subgroups
        )

        binding.apply {
            spinnerGroups.adapter = spinnerGroupAdapter
            spinnerSubgroups.adapter = spinnerSubgroupAdapter

            bConfirm.setOnClickListener {
                viewModel.getStudentScheduleWeek(
                    spinnerGroups.selectedItem.toString(),
                    spinnerSubgroups.selectedItem.toString()
                )
                activity?.onBackPressedDispatcher?.onBackPressed()
            }

        }
    }
}