package com.phaedra.myanimationsapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.phaedra.myanimationsapplication.MainActivity
import com.phaedra.myanimationsapplication.R

class SplashActivity : AppCompatActivity() {
    lateinit var motionLayout: MotionLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splaash)


        motionLayout=findViewById(R.id.parent)


        /**
         * We can call or listen motion layout listeners in an activity
         */
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout, i: Int, i1: Int) {

            }

            override fun onTransitionChange(motionLayout: MotionLayout, i: Int, i1: Int, v: Float) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, i: Int) {
                startIntent()
                //  motionLayout.transitionToStart();
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout,
                i: Int,
                b: Boolean,
                v: Float
            ) {
            }
        })



    }

        private fun startIntent() {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
}