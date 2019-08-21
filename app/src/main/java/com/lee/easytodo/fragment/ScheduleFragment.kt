package com.lee.easytodo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haibin.calendarview.CalendarView
import com.lee.easytodo.R
import com.lee.easytodo.fragment.base.BaseFragment

/**
 * A fragment shows the Schedule of user
 * with a calendar view
 */
class ScheduleFragment : BaseFragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerViewSchedule: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarView = view.findViewById(R.id.calendarView)
        recyclerViewSchedule = view.findViewById(R.id.recyclerViewSchedule)
        recyclerViewSchedule.setHasFixedSize(true)
    }

}
