package com.lee.easytodo.view

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import androidx.viewpager.widget.ViewPager
import com.lee.easytodo.view.calendarview.OnDraggedAboveListener

/**
 * @package com.lee.easytodo.view
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
class InteractDragEventViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    internal val EDGE_SCOPE = 150
    internal val TIMESTEP: Long = 1000

    internal var checkAlignEdgeLongX = 2f * EDGE_SCOPE
    internal var checkAlignEdgeLongY = 0f
    internal var checkAlignEdgeLongStartTime: Long = -1L

    internal var onDraggedEdgeListener: OnDraggedAboveListener? = null

    override fun onDragEvent(event: DragEvent?): Boolean {
        when (event?.action) {
            DragEvent.ACTION_DRAG_ENTERED -> {
                checkAlignEdgeLongX = event.x
                checkAlignEdgeLongY = event.y
            }
            DragEvent.ACTION_DRAG_LOCATION -> checkAlignEdgeLong(event.x, event.y)
            DragEvent.ACTION_DRAG_EXITED -> {
                //默认至于2倍EDGE_SCOPE的位置
                checkAlignEdgeLongX = 2f * EDGE_SCOPE
                checkAlignEdgeLongY = 0f
            }
            else -> {}
        }
        return true
    }

    private fun checkAlignEdgeLong(x: Float, y: Float) {
        if (isEdge(x)) {
            //坐标在edge范围
            if (checkAlignEdgeLongStartTime == -1L) {
                //说明计时未开始
                checkAlignEdgeLongStartTime = SystemClock.elapsedRealtime()
                checkAlignEdgeLongX = x
                checkAlignEdgeLongY = y
            } else {
                Log.d("lee_view", "time recording")
                //说明计时已经开始
                if (Math.abs(x - checkAlignEdgeLongX) > 30 || Math.abs(y - checkAlignEdgeLongY) > 30) {
                    Log.d("lee_view", "re-recording")
                    //位移太大，中断了操作，重新计时
                    checkAlignEdgeLongStartTime = SystemClock.elapsedRealtime()
                    checkAlignEdgeLongX = x
                    checkAlignEdgeLongY = y
                } else {
                    val newTime = SystemClock.elapsedRealtime()
                    Log.d("lee_view", "timegap: " + (newTime - checkAlignEdgeLongStartTime))
                    if (newTime - checkAlignEdgeLongStartTime > TIMESTEP) {
                        //触发操作
                        if (onDraggedEdgeListener != null) {
                            Log.d("lee_view", "call: onLongDraggedEdge")
                            var direction = OnDraggedAboveListener.Direction.RIGHT
                            if (x < EDGE_SCOPE) direction = OnDraggedAboveListener.Direction.LEFT
                            onDraggedEdgeListener?.onDraggedEdgeLong(direction)
                        }
                        //触发成功，重置判断条件
                        checkAlignEdgeLongX = 0f
                        checkAlignEdgeLongY = 0f
                        checkAlignEdgeLongStartTime = -1L
                    } else {
                        Log.d("lee_view", "waiting time")
                        //没到时间，但是位移小，未中断操作，
                        // 继续计时，为了保证成功率，更新坐标
                        checkAlignEdgeLongX = x
                        checkAlignEdgeLongY = y
                    }
                }
            }

        } else {
            //左边不在edge范围
            checkAlignEdgeLongStartTime = -1L
        }
    }

    private fun isEdge(x: Float) : Boolean{
        if (x < EDGE_SCOPE || x > width - EDGE_SCOPE) {
            Log.d("lee_view", "in edge x: $x")
            return true
        } else {
            return false
        }
    }

    fun setOnGraggedEdgeListener(listener: OnDraggedAboveListener) {
        this.onDraggedEdgeListener = listener
    }
}