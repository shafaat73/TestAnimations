
package com.phaedra.myanimationsapplication.ui

import android.R.color
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color.blue
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.phaedra.myanimationsapplication.databinding.ActivityCardsBinding


class CardsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCardsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = ActivityCardsBinding.inflate(layoutInflater)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        binding.cardView.setOnClickListener {
            toggle(true)
            toggleToolbar(false,binding.toolbar,-100f)
            toggleToolbar(false,binding.cardViewBack,1000f)
            binding.cardText.visibility=View.VISIBLE
            binding.view.visibility=View.GONE
        }
        binding.btnCheck.setOnClickListener {
            toggle(false)
            toggleToolbar(true,binding.toolbar,-100f)
            toggleToolbar(true,binding.cardViewBack,1000f)
            binding.cardText.visibility=View.GONE
            binding.toolbar.visibility=View.VISIBLE
            binding.view.visibility=View.VISIBLE
        }
        binding.cardViewBack.setOnClickListener {
            binding.cardViewBack.animate()
                .x( binding.view2.x)
                .y( binding.view2.y)
                .setDuration(1000)
                .withEndAction {
                    //to make sure that it arrives,
                    //but not needed actually these two lines
                    binding.cardViewBack.x =  binding.view2.x
                    binding.cardViewBack.y =  binding.view2.y
                }
                .start()
        }


        binding.cardText.setOnClickListener {
            val intent=Intent(this,MotionActivity::class.java)
            startActivity(intent)
        }

    }

    private fun toggle(show: Boolean) {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration = 400
        transition.addTarget(binding.btnCheck)
        TransitionManager.beginDelayedTransition(binding.parent, transition)
        binding.btnCheck.visibility = if (show) View.VISIBLE else View.GONE
    }



    private fun toggleToolbar(show: Boolean,view: View,value:Float) {
        if (!show) {
            //move position of a view with animation
           view.animate().alpha(0f).translationY(value).setDuration(600L)
                .withEndAction(Runnable { //binding.toolbar.visibility = View.GONE
                 }).start()
        } else {
            view.visibility = View.VISIBLE
            view.animate().alpha(1f).translationY(0f).setDuration(600L).start()
        }
    }

}