package com.yxl.schedule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yxl.schedule.databinding.DialogSearchBinding
import com.yxl.schedule.utils.Constants
import java.util.Locale

class SearchDialogFragment: Fragment() {

    private lateinit var binding: DialogSearchBinding
    private val viewModel: ScheduleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSearchBinding.inflate(inflater, container, false)
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
                    viewModel.getProfessorScheduleWeek(
                        if (etProf.text.isNotEmpty()){
                            etProf.text.toString().lowercase()
                                .replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                                    else it.toString()
                                }
                        }else{
                            null
                        }
                    )
                } else {
                    viewModel.getStudentScheduleWeek(
                        spinnerGroups.selectedItem.toString(),
                        spinnerSubgroups.selectedItem.toString()
                    )
                }
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }

        binding.ivClose.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchDialogFragment()
    }
}