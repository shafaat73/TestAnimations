package com.phaedra.myanimationsapplication.morphinButton

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.StateSet
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatButton
import com.phaedra.myanimationsapplication.R

open class MorphingButton : AppCompatButton {
    private var mPadding: Padding? = null
    private var mHeight = 0
    private var mWidth = 0
    private var mColor = 0
    private var mCornerRadius = 0f
    private var mStrokeWidth = 0
    private var mStrokeColor = 0
    protected var mAnimationInProgress = false
    private var mDrawableNormal: StrokeGradientDrawable? = null
    private var mDrawablePressed: StrokeGradientDrawable? = null

    constructor(context: Context?) : super(context!!) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mHeight == 0 && mWidth == 0 && w != 0 && h != 0) {
            mHeight = height
            mWidth = width
        }
    }

    val drawableNormal: StrokeGradientDrawable?
        get() = mDrawableNormal

    fun morph(@NonNull params: Params) {
        if (!mAnimationInProgress) {
            mDrawablePressed!!.color = params.colorPressed
            mDrawablePressed!!.setCornerRadius(params.cornerRadius)
            mDrawablePressed!!.strokeColor = params.strokeColor
            mDrawablePressed!!.strokeWidth = params.strokeWidth
            if (params.duration == 0) {
                morphWithoutAnimation(params)
            } else {
                morphWithAnimation(params)
            }
            mColor = params.color
            mCornerRadius = params.cornerRadius
            mStrokeWidth = params.strokeWidth
            mStrokeColor = params.strokeColor
        }
    }

    private fun morphWithAnimation(@NonNull params: Params) {
        mAnimationInProgress = true
        text = null
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        setPadding(mPadding!!.left, mPadding!!.top, mPadding!!.right, mPadding!!.bottom)
        val animationParams = MorphingAnimation.Params.create(this)
            .color(mColor, params.color)
            .cornerRadius(mCornerRadius, params.cornerRadius)
            .strokeWidth(mStrokeWidth, params.strokeWidth)
            .strokeColor(mStrokeColor, params.strokeColor)
            .height(height, params.height)
            .width(width, params.width)
            .duration(params.duration)
            .listener(object : MorphingAnimation.Listener {
                override fun onAnimationEnd() {
                    finalizeMorphing(params)
                }
            })
        val animation = MorphingAnimation(animationParams)
        animation.start()
    }

    private fun morphWithoutAnimation(@NonNull params: Params) {
        mDrawableNormal?.color = params.color
        mDrawableNormal?.setCornerRadius(params.cornerRadius)
        mDrawableNormal?.strokeColor = params.strokeColor
        mDrawableNormal?.strokeWidth = params.strokeWidth
        if (params.width != 0 && params.height != 0) {
            val layoutParams: ViewGroup.LayoutParams = layoutParams
            layoutParams.width = params.width
            layoutParams.height = params.height
            setLayoutParams(layoutParams)
        }
        finalizeMorphing(params)
    }

    private fun finalizeMorphing(@NonNull params: Params) {
        mAnimationInProgress = false
        if (params.icon != 0 && params.text != null) {
            setIconLeft(params.icon)
            text = params.text
        } else if (params.icon != 0) {
            setIcon(params.icon)
        } else if (params.text != null) {
            text = params.text
        }
        if (params.animationListener != null) {
            params.animationListener!!.onAnimationEnd()
        }
    }

    fun blockTouch() {
        setOnTouchListener { v, event -> true }
    }

    fun unblockTouch() {
        setOnTouchListener { v, event -> false }
    }

    private fun initView() {
        mPadding = Padding()
        mPadding!!.left = paddingLeft
        mPadding!!.right = paddingRight
        mPadding!!.top = paddingTop
        mPadding!!.bottom = paddingBottom
        val resources = resources
        val cornerRadius = resources.getDimension(R.dimen.mb_corner_radius_2).toFloat()
        val blue = resources.getColor(R.color.mb_blue)
        val blueDark = resources.getColor(R.color.mb_blue_dark)
        val background = StateListDrawable()
        mDrawableNormal = createDrawable(blue, cornerRadius, 0)
        mDrawablePressed = createDrawable(blueDark, cornerRadius, 0)
        mColor = blue
        mStrokeColor = blue
        mCornerRadius = cornerRadius
        background.addState(
            intArrayOf(android.R.attr.state_pressed),
            mDrawablePressed?.gradientDrawable
        )
        background.addState(StateSet.WILD_CARD, mDrawableNormal?.gradientDrawable)
        setBackgroundCompat(background)
    }

    private fun createDrawable(
        color: Int,
        cornerRadius: Float,
        strokeWidth: Int
    ): StrokeGradientDrawable {
        val drawable = StrokeGradientDrawable(GradientDrawable())
        drawable.gradientDrawable.shape = GradientDrawable.RECTANGLE
        drawable.color = color
        drawable.setCornerRadius(cornerRadius)
        drawable.strokeColor = color
        drawable.strokeWidth = strokeWidth
        return drawable
    }

    private fun setBackgroundCompat(@Nullable drawable: Drawable) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable)
        } else {
            background = drawable
        }
    }

    fun setIcon(@DrawableRes icon: Int) {
        // post is necessary, to make sure getWidth() doesn't return 0
        post {
            val drawable = resources.getDrawable(icon)
            val padding = width / 2 - drawable.intrinsicWidth / 2
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            setPadding(padding, 0, 0, 0)
        }
    }

    fun setIconLeft(@DrawableRes icon: Int) {
        setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
    }

    private inner class Padding {
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
    }

    class Params private constructor() {
        var cornerRadius = 0f
        var width = 0
        var height = 0
        var color = 0
         var colorPressed = 0
        var duration = 0
        var icon = 0
        var strokeWidth = 0
        var strokeColor = 0
        var text: String? = null
        var animationListener: MorphingAnimation.Listener? = null
        fun text(@NonNull text: String?): Params {
            this.text = text
            return this
        }

        fun icon(@DrawableRes icon: Int): Params {
            this.icon = icon
            return this
        }

        fun cornerRadius(cornerRadius: Float): Params {
            this.cornerRadius = cornerRadius
            return this
        }

        fun width(width: Int): Params {
            this.width = width
            return this
        }

        fun height(height: Int): Params {
            this.height = height
            return this
        }

        fun color(color: Int): Params {
            this.color = color
            return this
        }

        fun colorPressed(colorPressed: Int): Params {
            this.colorPressed = colorPressed
            return this
        }

        fun duration(duration: Int): Params {
            this.duration = duration
            return this
        }

        fun strokeWidth(strokeWidth: Int): Params {
            this.strokeWidth = strokeWidth
            return this
        }

        fun strokeColor(strokeColor: Int): Params {
            this.strokeColor = strokeColor
            return this
        }

        fun animationListener(animationListener: MorphingAnimation.Listener?): Params {
            this.animationListener = animationListener
            return this
        }

        companion object {
            fun create(): Params {
                return Params()
            }
        }
    }
}