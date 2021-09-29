package com.phaedra.myanimationsapplication.morphinButton

import android.graphics.drawable.GradientDrawable

class StrokeGradientDrawable(val gradientDrawable: GradientDrawable) {
    private var mStrokeWidth = 0
    private var mStrokeColor = 0
    var radius = 0f
        private set
    private var mColor = 0
    var strokeWidth: Int
        get() = mStrokeWidth
        set(strokeWidth) {
            mStrokeWidth = strokeWidth
            gradientDrawable.setStroke(strokeWidth, strokeColor)
        }
    var strokeColor: Int
        get() = mStrokeColor
        set(strokeColor) {
            mStrokeColor = strokeColor
            gradientDrawable.setStroke(strokeWidth, strokeColor)
        }

    fun setCornerRadius(radius: Float) {
        this.radius = radius
        gradientDrawable.cornerRadius = radius
    }

    var color: Int
        get() = mColor
        set(color) {
            mColor = color
            gradientDrawable.setColor(color)
        }
}