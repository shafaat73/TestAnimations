package com.phaedra.myanimationsapplication.morphinButton.impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.NonNull
import com.phaedra.myanimationsapplication.morphinButton.IProgress
import com.phaedra.myanimationsapplication.morphinButton.MorphingButton

class LinearProgressButton : MorphingButton, IProgress {
    private var mProgress = 0
    private var mProgressColor = 0
    private var mProgressCornerRadius = 0f
    private var mPaint: Paint? = null
    private var mRectF: RectF? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onDraw(@NonNull canvas: Canvas) {
        super.onDraw(canvas)
        if (!mAnimationInProgress && mProgress > MIN_PROGRESS && mProgress <= MAX_PROGRESS) {
            if (mPaint == null) {
                mPaint = Paint()
                mPaint!!.isAntiAlias = true
                mPaint!!.style = Paint.Style.FILL
                mPaint!!.color = mProgressColor
            }
            if (mRectF == null) {
                mRectF = RectF()
            }
            mRectF?.right = width / MAX_PROGRESS * mProgress.toFloat()
            mRectF?.bottom = height.toFloat()
            canvas.drawRoundRect(
                mRectF!!,
                mProgressCornerRadius.toFloat(),
                mProgressCornerRadius.toFloat(),
                mPaint!!
            )
        }
    }

   fun morphi(@NonNull params: Params?) {
        super.morph(params!!)
        mProgress = MIN_PROGRESS
        mPaint = null
        mRectF = null
    }


    override fun setProgress(progress: Int) {
        mProgress = progress
        invalidate()
    }

    fun morphToProgress(
        color: Int,
        progressColor: Int,
        progressCornerRadius: Float,
        width: Int,
        height: Int,
        duration: Int
    ) {
        mProgressCornerRadius = progressCornerRadius
        mProgressColor = progressColor
        val longRoundedSquare: MorphingButton.Params = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(mProgressCornerRadius)
            .width(width)
            .height(height)
            .color(color)
            .colorPressed(color)
        morph(longRoundedSquare)
    }

    companion object {
        const val MAX_PROGRESS = 100
        const val MIN_PROGRESS = 0
    }
}