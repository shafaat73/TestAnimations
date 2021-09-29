package com.phaedra.myanimationsapplication.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.databinding.ActivityButtonsBinding
import com.phaedra.myanimationsapplication.morphinButton.MorphingButton
import com.phaedra.myanimationsapplication.morphinButton.impl.IndeterminateProgressButton
import com.phaedra.myanimationsapplication.morphinButton.impl.LinearProgressButton
import com.phaedra.myanimationsapplication.utill.ProgressGenerator
import com.phaedra.myanimationsapplication.utill.ProgressGenerator.OnCompleteListener


class ButtonsActivity : AppCompatActivity() {
    lateinit var binding:ActivityButtonsBinding
    private var mMorphCounter1 = 1
    private var mMorphCounter2 = 1
    private var mMorphCounter1Linear= 1
    private var mMorphCounter2Linear = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityButtonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)


        binding.morphinBtn.setOnClickListener {

            onMorphButton1Clicked(binding.morphinBtn)
        }
        binding.morphinBtnError.setOnClickListener {

            onMorphButton2Clicked(binding.morphinBtnError)
        }

        binding.progressBtnSuccess.setOnClickListener {

            onMorphButton1LinearClicked(binding.progressBtnSuccess)
        }
        binding.progressBtnError.setOnClickListener {

            onMorphButton2LinearClicked(binding.progressBtnError)
        }

        // morphToSquare(binding.morphinBtn, 0);
        //morphToFailure(binding.morphinBtnError, 0);
    }



    fun dimen(@DimenRes resId: Int): Int {
        return resources.getDimension(resId).toInt()
    }

    fun color(@ColorRes resId: Int): Int {
        return resources.getColor(resId)
    }

    fun integer(@IntegerRes resId: Int): Int {
        return resources.getInteger(resId)
    }



    private fun onMorphButton1Clicked(btnMorph: IndeterminateProgressButton) {
        if (mMorphCounter1 === 0) {
            mMorphCounter1++
            morphToSquare(btnMorph, integer(com.phaedra.myanimationsapplication.R.integer.mb_animation))
        } else if (mMorphCounter1 === 1) {
            mMorphCounter1 = 0
            simulateProgress1(btnMorph)
        }
    }

    private fun onMorphButton1LinearClicked(btnMorph: LinearProgressButton) {
        if (mMorphCounter1Linear === 0) {
            mMorphCounter1Linear++
            morphToSquareLinear(btnMorph, integer(com.phaedra.myanimationsapplication.R.integer.mb_animation))
        } else if (mMorphCounter1Linear === 1) {
            mMorphCounter1Linear = 0
            simulateLinearProgress1(btnMorph)
        }
    }

    private fun onMorphButton2Clicked(btnMorph: IndeterminateProgressButton) {
        if (mMorphCounter2 === 0) {
            mMorphCounter2++
            morphToSquare(btnMorph, integer(com.phaedra.myanimationsapplication.R.integer.mb_animation))
        } else if (mMorphCounter2 === 1) {
            mMorphCounter2 = 0
            morphToFailure(btnMorph,700)
        }
    }
    private fun onMorphButton2LinearClicked(btnMorph: LinearProgressButton) {
        if (mMorphCounter2Linear === 0) {
            mMorphCounter2Linear++
            morphToSquareLinear(btnMorph, integer(com.phaedra.myanimationsapplication.R.integer.mb_animation))
        } else if (mMorphCounter2Linear === 1) {
            mMorphCounter2Linear = 0
            simulateProgress2Linear(btnMorph)
        }
    }

    /**
     * Simple Square Morph Button
     */
    private fun morphToSquare(btnMorph: IndeterminateProgressButton, duration: Int) {
        val square = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_corner_radius_2).toFloat())
            .width(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_width_100))
            .height(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .color(color(com.phaedra.myanimationsapplication.R.color.mb_blue))
            .colorPressed(color(com.phaedra.myanimationsapplication.R.color.mb_blue_dark))
            .text(getString(com.phaedra.myanimationsapplication.R.string.mb_button))
        btnMorph.morphi(square)
    }


    /**
     * Icon Morph Button
     */
    private fun morphToSuccess(btnMorph: IndeterminateProgressButton) {
        val circle = MorphingButton.Params.create()
            .duration(integer(com.phaedra.myanimationsapplication.R.integer.mb_animation))
            .cornerRadius(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56).toFloat())
            .width(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .height(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .color(color(com.phaedra.myanimationsapplication.R.color.mb_green))
            .colorPressed(color(com.phaedra.myanimationsapplication.R.color.mb_green_dark))
            .icon(com.phaedra.myanimationsapplication.R.drawable.ic_progress_done)
        btnMorph.morphi(circle)
    }

    private fun morphToSquareLinear(btnMorph: LinearProgressButton, duration: Int) {
        val square = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_corner_radius_2).toFloat())
            .width(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_width_100))
            .height(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .color(color(com.phaedra.myanimationsapplication.R.color.mb_blue))
            .colorPressed(color(com.phaedra.myanimationsapplication.R.color.mb_blue_dark))
            .text(getString(com.phaedra.myanimationsapplication.R.string.mb_button))
        btnMorph.morphi(square)
    }

    private fun morphToSuccessLinear(btnMorph: LinearProgressButton) {
        val circle = MorphingButton.Params.create()
            .duration(integer(com.phaedra.myanimationsapplication.R.integer.mb_animation))
            .cornerRadius(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56).toFloat())
            .width(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .height(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .color(color(com.phaedra.myanimationsapplication.R.color.mb_green))
            .colorPressed(color(com.phaedra.myanimationsapplication.R.color.mb_green_dark))
            .icon(com.phaedra.myanimationsapplication.R.drawable.ic_progress_done)
        btnMorph.morphi(circle)
    }

    private fun morphToFailure(btnMorph: IndeterminateProgressButton, duration: Int) {
        val circle = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56).toFloat())
            .width(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .height(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .color(color(com.phaedra.myanimationsapplication.R.color.mb_red))
            .colorPressed(color(com.phaedra.myanimationsapplication.R.color.mb_red_dark))
            .icon(com.phaedra.myanimationsapplication.R.drawable.ic_baseline_lock_24)
        btnMorph.morphi(circle)
    }
    private fun morphToFailureLinear(btnMorph: LinearProgressButton, duration: Int) {
        val circle = MorphingButton.Params.create()
            .duration(duration)
            .cornerRadius(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56).toFloat())
            .width(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .height(dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_56))
            .color(color(com.phaedra.myanimationsapplication.R.color.mb_red))
            .colorPressed(color(com.phaedra.myanimationsapplication.R.color.mb_red_dark))
            .icon(com.phaedra.myanimationsapplication.R.drawable.ic_baseline_lock_24)
        btnMorph.morphi(circle)
    }


    /**
     * Animated Progress
     */
    private fun simulateProgress2(button: IndeterminateProgressButton) {
        val progressColor = color(com.phaedra.myanimationsapplication.R.color.mb_blue)
        val color = color(com.phaedra.myanimationsapplication.R.color.mb_gray)
        val progressCornerRadius = dimen(com.phaedra.myanimationsapplication.R.dimen.mb_corner_radius_4)
        val width = dimen(com.phaedra.myanimationsapplication.R.dimen.mb_width_200)
        val height = dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_8)
        val duration = integer(com.phaedra.myanimationsapplication.R.integer.mb_animation)
        val handler = Handler()
        handler.postDelayed(Runnable {
            morphToFailure(button, integer(com.phaedra.myanimationsapplication.R.integer.mb_animation))
            button.unblockTouch()
        }, 4000)
        button.blockTouch() // prevent user from clicking while button is in progress
        button.morphToProgress(
            color,
            progressCornerRadius.toFloat(),
            width,
            height,
            duration,
            progressColor
        )
    }

    private fun simulateProgress1(button: IndeterminateProgressButton) {
        val progressColor1 = color(android.R.color.holo_blue_bright)
        val progressColor2 = color(android.R.color.holo_green_light)
        val progressColor3 = color(android.R.color.holo_orange_light)
        val progressColor4 = color(android.R.color.holo_red_light)
        val color = color(com.phaedra.myanimationsapplication.R.color.mb_gray)
        val progressCornerRadius = dimen(com.phaedra.myanimationsapplication.R.dimen.mb_corner_radius_4)
        val width = dimen(com.phaedra.myanimationsapplication.R.dimen.mb_width_200)
        val height = dimen(com.phaedra.myanimationsapplication.R.dimen.mb_height_8)
        val duration = integer(com.phaedra.myanimationsapplication.R.integer.mb_animation)
        val handler = Handler()
        handler.postDelayed(Runnable {
            morphToSuccess(button)
            val intent=Intent(this,ViewPagerActivity::class.java)
            startActivity(intent)
            button.unblockTouch()
        }, 4000)
        button.blockTouch() // prevent user from clicking while button is in progress
        button.morphToProgress(
            color,
            progressCornerRadius.toFloat(),
            width,
            height,
            duration,
            progressColor1,
            progressColor2,
            progressColor3,
            progressColor4
        )
    }


    private fun simulateLinearProgress1(button: LinearProgressButton) {
        val progressColor = color(R.color.mb_purple)
        val color = color(R.color.mb_gray)
        val progressCornerRadius = dimen(R.dimen.mb_corner_radius_4)
        val width = dimen(R.dimen.mb_width_200)
        val height = dimen(R.dimen.mb_height_8)
        val duration = integer(R.integer.mb_animation)
        val generator = ProgressGenerator(object : ProgressGenerator.OnCompleteListener {
            override fun onComplete() {
                morphToSuccessLinear(button)
                button.unblockTouch()
            }
        })
        button.blockTouch() // prevent user from clicking while button is in progress
        button.morphToProgress(
            color,
            progressColor,
            progressCornerRadius.toFloat(),
            width,
            height,
            duration
        )
        generator.start(button)
    }
    private fun simulateProgress2Linear(button: LinearProgressButton) {
        val progressColor = color(R.color.mb_purple)
        val color = color(R.color.mb_gray)
        val progressCornerRadius = dimen(R.dimen.mb_corner_radius_4)
        val width = dimen(R.dimen.mb_width_200)
        val height = dimen(R.dimen.mb_height_8)
        val duration = integer(R.integer.mb_animation)
        val generator = ProgressGenerator(object : OnCompleteListener {
            override fun onComplete() {
                morphToFailureLinear(button, integer(R.integer.mb_animation))
                button.unblockTouch()
            }
        })
        button.blockTouch() // prevent user from clicking while button is in progress
        button.morphToProgress(
            color,
            progressColor,
            progressCornerRadius.toFloat(),
            width,
            height,
            duration
        )
        generator.start(button)
    }



}