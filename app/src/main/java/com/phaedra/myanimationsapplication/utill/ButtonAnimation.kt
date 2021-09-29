package com.phaedra.myanimationsapplication.utill

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup


class ButtonAnimation(private val buttonExpandedWidth: Int, private val animationTime: Long) {



    /**
     * Fade in view and visible another view on animation end
     * value range from 0f to 1f , 0 for fade in 1 for fade out
     *
     * @param view
     * @param visibleView
     */
    fun onFadeInVisibleView(view: View, visibleView: View) {
        view.animate()
            .alpha(0f)
            .setDuration(animationTime)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    visibleView.visibility = View.VISIBLE
                }
            })
            .start()
    }


    /**
     * Fade out view and visible another view on animation end.
     * value range from 0f to 1f , 0 for fade in 1 for fade out
     *
     * @param view
     * @param visibleView
     */
    fun onFadeOutVisibleView(view: View, visibleView: View) {
        view.animate()
            .alpha(0f)
            .setDuration(animationTime)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    visibleView.visibility = View.VISIBLE
                }
            })
            .start()
    }


    /**
     * Fade out view.
     *
     * @param view
     */
    fun fadeOutView(view: View) {
        view.animate()
            .alpha(1f)
            .setDuration(animationTime)
            .start()
    }


    /**
     * Fade in view.
     *
     * @param view
     */
    fun fadeInView(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(animationTime)
            .start()
    }


    /**
     * Animate view to get shrink effect.
     *
     * @param view
     */
    fun animateShapeShrink(view: View) {
        val anim = ValueAnimator.ofInt(view.measuredWidth, view.measuredHeight)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.width = `val`
            view.requestLayout()
        }
        anim.duration = animationTime.toLong()
        anim.start()
    }


    /**
     * Animate view to get expand effect.
     *
     * @param view
     */
    fun animateShapeExpand(view: View) {
        val anim = ValueAnimator.ofInt(view.measuredWidth, buttonExpandedWidth)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.width = `val`
            view.requestLayout()
        }
        anim.duration = animationTime.toLong()
        anim.start()
    }


}