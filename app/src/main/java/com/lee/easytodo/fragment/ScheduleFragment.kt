package com.lee.easytodo.fragment


import android.content.ClipData
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lee.easytodo.R
import com.lee.easytodo.fragment.base.BaseFragment
import com.lee.easytodo.presenter.ScheduleFragmentPresenter
import com.lee.easytodo.util.DragActionInteractor
import com.lee.easytodo.util.logD
import com.lee.easytodo.view.calendarview.CalendarView
import com.lee.easytodo.view.drag.InteractDragEventRecyclerView

/**
 * A fragment shows the Schedule of user
 * with a calendar view
 */
class ScheduleFragment : BaseFragment() {

    private lateinit var presenter: ScheduleFragmentPresenter

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerViewSchedule: InteractDragEventRecyclerView
    private var adapter: RecyclerView.Adapter<ViewHolder>? = null
    private val dataSet = ArrayList<ClipData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = ScheduleFragmentPresenter(this)
        presenter.load(view)
    }

    override fun onActivityInteraction(clipData: ClipData) {
        clipData.description
        logD("get material")
        //TODO add to data set
        dataSet.add(clipData)
        //TODO notify
        adapter?.notifyDataSetChanged()
        //TODO animation
        logD("dataset.size: ${dataSet.size}")

        when (clipData.description.label) {

        }
    }

    override fun initView(root: View) {
        calendarView = root.findViewById(R.id.calendarView)
        recyclerViewSchedule = root.findViewById(R.id.recyclerViewSchedule)
        recyclerViewSchedule.setHasFixedSize(true)
//        for (i in 1..10) {
//            dataSet.add(ClipData("this is $i", arrayOf("arr[$i]"), ClipData.Item("item: $i")))
//        }
        adapter = object : RecyclerView.Adapter<ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                var view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_recycler_main_activity_schedule_fragment_layout,
                    parent,
                    false)
                return ViewHolder(view)
            }

            override fun getItemCount(): Int {
                return dataSet.size
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {

                holder.textViewTitle.setText(dataSet[position].getItemAt(0).text)

                holder.root.setOnLongClickListener { v ->

                    v?.visibility = View.VISIBLE

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        v?.startDragAndDrop(dataSet[position], View.DragShadowBuilder(v), v, 0)
                    } else {
                        v?.startDrag(dataSet[position], View.DragShadowBuilder(v), v, 0)
                    }
                    true
                }
            }

        }
        recyclerViewSchedule.adapter = adapter
        recyclerViewSchedule.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun initAction() {
        calendarView.setDragActionInteractor(
            DragActionInteractor.Companion.Builder()
                .isInteractStayEdgeLong(true)
                .isInteractLocationChange(true)
                .isInteractDrop(true)
                .onDragInteractListener(object : DragActionInteractor.OnDragActionListener {
                    override fun onDrop(x: Float, y: Float, clipData: ClipData) {}

                    override fun onLocationChanged(x: Float, y: Float) {}

                    override fun onStayEdgeLong(direction: DragActionInteractor.DIRECTION) {
                        when (direction) {
                            DragActionInteractor.DIRECTION.LEFT -> calendarView.scrollToPre(true)
                            DragActionInteractor.DIRECTION.RIGHT -> calendarView.scrollToNext(true)
                        }
                    }

                })
                .build())
        recyclerViewSchedule.dragActionInteractor = DragActionInteractor.Companion.Builder()
            .isInteractLocationChange(true)
            .isInteractDrop(true)
            .isInteractStayEdgeLong(false)
            .onDragInteractListener(object : DragActionInteractor.OnDragActionListener {
                override fun onDrop(x: Float, y: Float, clipData: ClipData) {
                    logD("todo drop  $clipData")
                }

                override fun onLocationChanged(x: Float, y: Float) {
                    logD("todo location x:$x, y:$y")
                }

                override fun onStayEdgeLong(direction: DragActionInteractor.DIRECTION) {}

            })
            .build()
    }

    companion object {
        class ViewHolder : RecyclerView.ViewHolder {
            val textViewTitle: TextView
            val root: View

            constructor(itemView: View) : super(itemView) {
                root = itemView
                textViewTitle = itemView.findViewById(R.id.textViewTitle)
            }
        }
    }
}
