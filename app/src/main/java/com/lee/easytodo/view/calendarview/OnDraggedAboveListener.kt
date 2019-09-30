package com.lee.easytodo.view.calendarview

import android.content.ClipData

/**
 * @package com.lee.easytodo.view.calendarview
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
interface OnDraggedAboveListener {
    /**
     * view dragged above edge long
     */
    fun onDraggedEdgeLong(direction: Direction)

    /**
     *
     */
    fun onDrop(calendar: Calendar, clipData: ClipData)
    fun onLocationChanged(x: Float, y: Float, calendar: Calendar)

    enum class Direction{
        LEFT,RIGHT
    }
}