package com.yxl.schedule.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yxl.schedule.databinding.DialogProfessorBinding
import com.yxl.schedule.ui.ScheduleViewModel
import java.util.Locale

class ProfessorDialogFragment : Fragment() {

    private lateinit var binding: DialogProfessorBinding
    private val viewModel: ScheduleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogProfessorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            bConfirm.setOnClickListener {
                viewModel.getProfessorScheduleWeek(
                    if (etProf.text.isNotEmpty()) {
                        etProf.text.toString().lowercase().replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                            else it.toString()
                        }
                    } else {
                        null
                    }
                )
            }
        }
    }
}