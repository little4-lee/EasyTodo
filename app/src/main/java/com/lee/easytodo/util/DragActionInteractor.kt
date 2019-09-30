package com.lee.easytodo.util

import android.content.ClipData
import android.os.SystemClock
import android.util.Log
import android.view.DragEvent
import kotlin.math.abs

/**
 * @package com.lee.easytodo.util
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
class DragActionInteractor {

    var dragX: Float = DEFAULT_POSITION
    var dragY: Float = DEFAULT_POSITION

    private var checkStayEdgeLongX = DEFAULT_POSITION
    private var checkStayEdgeLongY = DEFAULT_POSITION

    private var checkStayEdgeLongStartTime = DEFAULT_CHECK_STAY_EDGE_LONG_START_TIME

    private var edgeScope = DEFAULT_EDGE_SCOPE
    private var timeStepStayLong = DEFAULT_TIME_STEP_STAY_LONG
    private var stayLongScope = DEFAULT_STAY_LONG_SCOPE
    private var locationUpdateScope = DEFAULT_LOCATION_UPDATE_SCOPE

    var viewWidth = 0f
    private var isInteractStayEdgeLong = false
    private var isInteractDrop = false
    private var isInteractLocationChange = false

    private var onDragActionListener: OnDragActionListener? = null

    fun onDragEvent(event: DragEvent?): Boolean {
        //如果所有回调都不需要，则返回false
        if (!isInteractDrop && !isInteractLocationChange && !isInteractStayEdgeLong) return false

        when (event?.action) {
            DragEvent.ACTION_DRAG_ENTERED -> {
                if (isInteractStayEdgeLong) {
                    checkStayEdgeLongX = event.x
                    checkStayEdgeLongY = event.y
                }

                if (isInteractLocationChange) {
                    dragX = event.x
                    dragY = event.y
                }
            }

            DragEvent.ACTION_DROP -> {
                if (isInteractDrop) {
                    onDragActionListener?.onDrop(event.x, event.y, event.clipData)
                }
                resetData()
            }

            DragEvent.ACTION_DRAG_LOCATION -> {
                if (isInteractStayEdgeLong) checkAlignEdgeLong(event.x, event.y)
                if (isInteractLocationChange) checkLocationUpdate(event.x, event.y)
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                resetData()
            }
            else -> {

            }
        }
        return true
    }

    fun updateDragLocation(x: Float, y: Float) {
        dragX = x
        dragY = y
    }

    fun resetData() {
        dragX = DEFAULT_POSITION
        dragY = DEFAULT_POSITION
        checkStayEdgeLongX = DEFAULT_POSITION
        checkStayEdgeLongY = DEFAULT_POSITION
        checkStayEdgeLongStartTime = DEFAULT_CHECK_STAY_EDGE_LONG_START_TIME
    }

    fun checkAlignEdgeLong(x: Float, y: Float): Boolean {
        var isActionCalled = false
        if (isEdge(x)) {
            //坐标在edge范围
            if (checkStayEdgeLongStartTime == DEFAULT_CHECK_STAY_EDGE_LONG_START_TIME) {
                //说明计时未开始
                checkStayEdgeLongStartTime = SystemClock.elapsedRealtime()
                checkStayEdgeLongX = x
                checkStayEdgeLongY = y
            } else {
//                Log.d("lee_view", "time recording")
                //说明计时已经开始
                if (Math.abs(x - checkStayEdgeLongX) > stayLongScope || Math.abs(y - checkStayEdgeLongY) > stayLongScope) {
//                    Log.d("lee_view", "re-recording")
                    //位移太大，中断了操作，重新计时
                    checkStayEdgeLongStartTime = SystemClock.elapsedRealtime()
                    checkStayEdgeLongX = x
                    checkStayEdgeLongY = y
                } else {
                    val newTime = SystemClock.elapsedRealtime()
//                    Log.d("lee_view", "timegap: " + (newTime - checkStayEdgeLongStartTime))
                    if (newTime - checkStayEdgeLongStartTime > timeStepStayLong) {
                        //触发操作
                        if (onDragActionListener != null) {
                            Log.d("lee_view", "call: onLongDraggedEdge")
                            var direction = DIRECTION.RIGHT
                            if (x < edgeScope) direction = DIRECTION.LEFT
                            onDragActionListener?.onStayEdgeLong(direction)
                        }
                        //触发成功，重置判断条件
                        checkStayEdgeLongX = DEFAULT_POSITION
                        checkStayEdgeLongY = DEFAULT_POSITION
                        checkStayEdgeLongStartTime = DEFAULT_CHECK_STAY_EDGE_LONG_START_TIME
                        isActionCalled = true
                    } else {
//                        Log.d("lee_view", "waiting time")
                        //没到时间，但是位移小，未中断操作，
                        // 继续计时，为了保证成功率，更新坐标
                        checkStayEdgeLongX = x
                        checkStayEdgeLongY = y
                    }
                }
            }

        } else {
            //左边不在edge范围
            checkStayEdgeLongStartTime = DEFAULT_CHECK_STAY_EDGE_LONG_START_TIME
        }
        return isActionCalled
    }

    fun checkLocationUpdate(x: Float, y: Float): Boolean {
        if (abs(x - dragX) > locationUpdateScope || abs(y - dragY) > locationUpdateScope) {
            dragX = x
            dragY = y
            if (onDragActionListener != null) {
                onDragActionListener?.onLocationChanged(dragX, dragY)
            }
            return true
        }
        return false
    }

    private fun isEdge(x: Float): Boolean {
        if (x < edgeScope || x > viewWidth - edgeScope) {
            Log.d("lee_view", "$this width:$viewWidth in edge x: $x")
            return true
        } else {
            return false
        }
    }

    companion object {

        const val DEFAULT_EDGE_SCOPE = 150F
        const val DEFAULT_TIME_STEP_STAY_LONG = 1000L
        const val DEFAULT_TIME_STEP_STAY_LONG_SHORT = 500L
        const val DEFAULT_POSITION = 10000F
        const val DEFAULT_CHECK_STAY_EDGE_LONG_START_TIME = -1L
        const val DEFAULT_STAY_LONG_SCOPE = 30
        const val DEFAULT_LOCATION_UPDATE_SCOPE = 50


        open class Builder {
            private var isInteractStayEdgeLong = false
            private var isInteractDrop = false
            private var isInteractLocationChange = false
            private var edgeScope = DEFAULT_EDGE_SCOPE
            private var timeStepStayLong = DEFAULT_TIME_STEP_STAY_LONG
            private var viewWidth = 0f
            private var onDragActionListener: OnDragActionListener? = null
            private var stayLongScope = DEFAULT_STAY_LONG_SCOPE
            private var locationUpdateScope = DEFAULT_LOCATION_UPDATE_SCOPE


            fun edgeScope(scope: Float): Builder {
                this.edgeScope = scope
                return this
            }

            fun timeStepStayLong(time: Long): Builder {
                this.timeStepStayLong = time
                return this
            }

            fun stayLongScope(scope: Int): Builder {
                this.stayLongScope = scope
                return this
            }

            fun locationUpdateScope(scope: Int): Builder {
                this.locationUpdateScope = scope
                return this
            }

            fun isInteractStayEdgeLong(flag: Boolean): Builder {
                this.isInteractStayEdgeLong = flag
                return this
            }

            fun isInteractDrop(flag: Boolean): Builder {
                this.isInteractDrop = flag
                return this
            }

            fun isInteractLocationChange(flag: Boolean): Builder {
                this.isInteractLocationChange = flag
                return this
            }

            fun viewWidth(width: Float): Builder {
                this.viewWidth = width
                return this
            }

            fun onDragInteractListener(listener: OnDragActionListener): Builder {
                this.onDragActionListener = listener
                return this
            }

            fun build(): DragActionInteractor {
                var instance = DragActionInteractor()
                instance.isInteractStayEdgeLong = this.isInteractStayEdgeLong
                instance.isInteractDrop = this.isInteractDrop
                instance.isInteractLocationChange = this.isInteractLocationChange
                instance.edgeScope = this.edgeScope
                instance.timeStepStayLong = this.timeStepStayLong
                instance.viewWidth = this.viewWidth
                instance.onDragActionListener = this.onDragActionListener
                instance.locationUpdateScope = this.locationUpdateScope
                instance.stayLongScope = this.stayLongScope
                return instance
            }
        }
    }

    interface OnDragActionListener {
        /**
         * called when the dragged view dropped
         */
        fun onDrop(x: Float, y: Float, clipData: ClipData)

        /**
         * called when the dragged view's location changed
         */
        fun onLocationChanged(x: Float, y: Float)

        /**
         * called when the dragged view stayed on edge for long
         */
        fun onStayEdgeLong(direction: DIRECTION)
    }

    enum class DIRECTION {
        LEFT,
        RIGHT
    }
}