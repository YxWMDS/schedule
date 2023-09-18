package com.yxl.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yxl.schedule.R
import com.yxl.schedule.adapters.ViewPagerAdapter
import com.yxl.schedule.databinding.ActivityScheduleBinding
import com.yxl.schedule.ui.dialog.SearchDialogFragment
import com.yxl.schedule.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleBinding
    private lateinit var adapter: ViewPagerAdapter
    private val viewModel: ScheduleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewPager()
        setUpToolbar()

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.weekNumber.value = viewModel.weekNumber.value!! + tab?.position!! + 1
                viewModel.onWeekChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                viewModel.weekNumber.value = viewModel.weekNumber.value!! - tab?.position!! - 1
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        viewModel.isGroupsLoaded.observe(this){
            binding.fabSearchGroup.isEnabled = it
        }
        binding.fabSearchGroup.setOnClickListener { openSearchDialog() }
    }

    private fun openSearchDialog(){
        supportFragmentManager.beginTransaction()
            .add(R.id.containerView, SearchDialogFragment.newInstance())
            .addToBackStack("search_dialog")
            .commit()
    }

    private fun setUpToolbar() = with(binding){
        toolbar.toolbarBack.isVisible = false
        viewModel.weekNumber.observe(this@ScheduleActivity){
            toolbar.toolbarWeek.text = "$it Неделя"
        }
    }

    private fun setUpViewPager(){
        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.pager.adapter = adapter
        binding.pager.setCurrentItem(DateUtils.week - 1, true)

        TabLayoutMediator(binding.tabLayout, binding.pager){tab, position ->
            tab.text = DateUtils.setUpDate(position, binding.pager.currentItem)
        }.attach()
    }


}