package com.yxl.schedule.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.yxl.schedule.adapters.DialogViewPagerAdapter
import com.yxl.schedule.databinding.DialogSearchBinding

class SearchDialogFragment: Fragment() {

    private lateinit var binding: DialogSearchBinding
    private lateinit var pagerAdapter: DialogViewPagerAdapter

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
        setUpViewPager()
    }

    private fun setUpViewPager() {
        pagerAdapter = DialogViewPagerAdapter(parentFragmentManager, lifecycle)
        binding.dialogPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tab, binding.dialogPager){tab, position ->
            tab.text = when(position){
                0 -> "Студент"
                1 -> "Преподаватель"
                else -> ""
            }
        }.attach()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchDialogFragment()
    }
}