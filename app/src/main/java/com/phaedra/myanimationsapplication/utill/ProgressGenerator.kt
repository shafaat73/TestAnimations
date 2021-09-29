package com.phaedra.myanimationsapplication.utill

import android.os.Handler
import com.phaedra.myanimationsapplication.morphinButton.IProgress
import java.util.*

class ProgressGenerator(private val mListener: OnCompleteListener) {
    interface OnCompleteListener {
        fun onComplete()
    }

    private var mProgress = 0
    fun start(button: IProgress, duration: Int) {
        mProgress = 0
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                mProgress += 5
                button.setProgress(mProgress)
                if (mProgress < 100) {
                    handler.postDelayed(this, generateDelay().toLong())
                } else {
                    mListener.onComplete()
                }
            }
        }, duration.toLong())
    }

    fun start(button: IProgress) {
        start(button, 500)
    }

    private val random = Random()
    private fun generateDelay(): Int {
        return random.nextInt(100)
    }
}