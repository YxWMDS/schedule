package com.yxl.schedule.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yxl.schedule.ui.dialog.ProfessorDialogFragment
import com.yxl.schedule.ui.dialog.StudentDialogFragment

class DialogViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                StudentDialogFragment()
            }

            1 -> {
                ProfessorDialogFragment()
            }

            else -> StudentDialogFragment()
        }
    }
}