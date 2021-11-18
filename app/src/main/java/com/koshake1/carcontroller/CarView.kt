package com.koshake1.carcontroller

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35
private const val CAR_HEIGHT = 10
private const val CAR_WIDTH = 20

class CarView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f
    private var fanSpeed = Speed.OFF
    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = if (fanSpeed == Speed.OFF) Color.GRAY else Color.GREEN

        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
        paint.color = Color.BLACK
        canvas?.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)
        pointPosition.computeXYForSpeed(Speed.MEDIUM, markerRadius)
        canvas?.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)

    }

    private fun PointF.computeXYForSpeed(pos: Speed, radius: Float) {
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * kotlin.math.cos(angle)).toFloat() + width / 2
        y = (radius * kotlin.math.sin(angle)).toFloat() + height / 2
    }
}