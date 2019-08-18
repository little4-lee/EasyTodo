package com.lee.easytodo.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lee.easytodo.R
import com.lee.easytodo.activity.base.BaseActivity
import com.lee.easytodo.fragment.IdeaPoolFragment
import com.lee.easytodo.fragment.ScheduleFragment

/**
 * MainActivity of things
 */
class MainActivity : BaseActivity() {

    private lateinit var scheduleFragment: ScheduleFragment
    private lateinit var ideaPoolFragment: IdeaPoolFragment

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var viewPagerContent: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        initView()
    }

    private fun initView() {
        floatingActionButton = findViewById(R.id.floatingActionButton)
        viewPagerContent = findViewById(R.id.viewPagerContent)
    }

    private fun initEvent() {

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}
