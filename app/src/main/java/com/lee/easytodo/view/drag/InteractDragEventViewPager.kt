package com.lee.easytodo.view.drag

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import androidx.viewpager.widget.ViewPager
import com.lee.easytodo.util.DragActionInteractor

/**
 * @package com.lee.easytodo.view
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
class InteractDragEventViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    var dragActionInteractor: DragActionInteractor? = null

    override fun onDragEvent(event: DragEvent?): Boolean {
        if (dragActionInteractor == null) return false
        else return dragActionInteractor?.onDragEvent(event)!!
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        dragActionInteractor?.viewWidth = measuredWidth.toFloat()
    }
}