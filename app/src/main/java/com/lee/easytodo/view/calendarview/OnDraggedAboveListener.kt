package com.lee.easytodo.view.calendarview

/**
 * @package com.lee.easytodo.view.calendarview
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
interface OnDraggedAboveListener {
    fun onDraggedEdgeLong(direction: Direction)
    enum class Direction{
        LEFT,RIGHT
    }
}