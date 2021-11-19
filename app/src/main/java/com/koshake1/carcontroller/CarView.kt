package com.koshake1.carcontroller

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

private const val RADIUS_OFFSET_INDICATOR = -25

class CarView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        isClickable = true
    }

    private var radius = 0.0f
    private var angle = 0
    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    private val carBitMap = BitmapFactory.decodeResource(context.resources, R.drawable.car_blue)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(angle, markerRadius)
        paint.color = Color.BLACK
        canvas?.drawBitmap(carBitMap, pointPosition.x, pointPosition.y, paint)
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true

        angle++
        invalidate()
        return true

    }

    private fun PointF.computeXYForSpeed(pos: Int, radius: Float) {
        val startAngle = Math.PI/4
        val angle = startAngle + pos * (Math.PI / 4)
        x = (radius * kotlin.math.cos(angle)).toFloat() + width / 2
        y = (radius * kotlin.math.sin(angle)).toFloat() + height / 2
    }
}