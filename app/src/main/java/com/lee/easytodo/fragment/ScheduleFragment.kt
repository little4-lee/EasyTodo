package com.lee.easytodo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lee.easytodo.R
import com.lee.easytodo.fragment.base.BaseFragment

/**
 * A fragment shows the Schedule of user
 * with a calendar view
 */
class ScheduleFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule_layout, container, false)
    }

}
