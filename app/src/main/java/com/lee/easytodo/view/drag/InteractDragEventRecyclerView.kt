package com.lee.easytodo.view.drag

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import androidx.recyclerview.widget.RecyclerView
import com.lee.easytodo.util.DragActionInteractor

/**
 * @com www.weicheche.cn
 * @package com.lee.easytodo.view.drag
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
class InteractDragEventRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle)

    var dragActionInteractor: DragActionInteractor? = null

    override fun onDragEvent(event: DragEvent?): Boolean {
        if (dragActionInteractor != null) return dragActionInteractor!!.onDragEvent(event)
        else return super.onDragEvent(event)
    }
}