package com.koshake1.carcontroller

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

private const val RADIUS_OFFSET_INDICATOR = -35
private const val ANIMATION_DURATION = 2000L
private const val RADIUS_COEF = 0.75
private const val MAX_POS = 10.5f
private const val MIN_POS = 0.0f

class CarView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        isClickable = true
    }

    private lateinit var animator: ValueAnimator
    private var radius = 0.0f
    private var newPosition = 0.0f
    private val pointPosition: PointF = PointF(0.0f, 0.0f)
    private val carBitMap = BitmapFactory.decodeResource(context.resources, R.drawable.car_blue)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(width, height) / 2.0 * RADIUS_COEF).toFloat()
        initAnimator()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXY(newPosition, markerRadius)
        paint.color = Color.BLACK
        canvas?.drawBitmap(carBitMap, pointPosition.x, pointPosition.y, paint)
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true

        animator.start()
        return true

    }

    private fun PointF.computeXY(pos: Float, radius: Float) {
        val angle = newPosition + pos * (Math.PI / 4)
        x = (radius * kotlin.math.cos(angle)).toFloat() + width / 2
        y = (radius * kotlin.math.sin(angle)).toFloat() + height / 2
    }

    private fun initAnimator() {
        animator = ValueAnimator.ofFloat(MIN_POS, MAX_POS)
        animator.duration = ANIMATION_DURATION
        animator.addUpdateListener {
            newPosition = it.animatedValue as Float
            invalidate()
        }
    }
}