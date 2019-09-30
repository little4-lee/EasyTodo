package com.lee.easytodo.view.animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.lee.easytodo.util.logD

/**
 * @com www.weicheche.cn
 * @package com.lee.easytodo.view.animation
 * @author zhangjianfei
 * @create 2019-08-25.
 * @function
 */
class TextGeneratingView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var progress = 0f

    fun setProgress(progress: Float) {
        logD("setProgress: $progress")
        this.progress = progress
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        radiusCircle = measuredHeight / 2.toFloat()
        widthRect = measuredWidth - 2 * radiusCircle
        logD("width: $widthRect, radius: $radiusCircle")
    }

    private var widthRect = 0f
    private var radiusCircle = 0f
    private lateinit var paint: Paint

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.color = Color.LTGRAY

        when (progress) {
            0f -> canvas?.drawColor(Color.parseColor("#00FFFFFF"))
            in 1f..80f -> {
                //画矩形
                var rectLeft = (progress / 80) * widthRect
                var rectTop = 0f
                var rectRight = widthRect
                var rectBottom = measuredHeight.toFloat()
                logD("rect: $rectLeft, $rectTop, $rectRight, $rectBottom")
                canvas?.drawRoundRect(
                    rectLeft,
                    rectTop,
                    rectRight,
                    rectBottom,
                    15f,
                    15f,
                    paint
                )
            }
            in 81f..99f -> {
                //画圆
                canvas?.drawCircle(
                    widthRect + radiusCircle,
                    radiusCircle,
                    radiusCircle * (0.5f + 0.5f * (progress - 80) / 20),
                    paint
                )
            }
            100f -> {
                var path = Path()
                path.moveTo(widthRect, radiusCircle)
                path.rLineTo(2 * radiusCircle, -radiusCircle)
                path.rLineTo(-radiusCircle, radiusCircle)
                path.rLineTo(radiusCircle, radiusCircle)
                canvas?.drawPath(path, paint)
            }
            else -> {
            }

        }
    }
}