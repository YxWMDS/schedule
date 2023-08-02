package com.yxl.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yxl.schedule.databinding.ActivityMainBinding
import com.yxl.schedule.ui.ScheduleFragment

class ScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, ScheduleFragment.newInstance())
                .commit()
        }
    }
}