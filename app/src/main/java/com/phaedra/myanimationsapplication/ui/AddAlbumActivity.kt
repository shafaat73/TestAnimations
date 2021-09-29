package com.phaedra.myanimationsapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.phaedra.myanimationsapplication.MainActivity
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.databinding.ActivityAddAlbumBinding


class AddAlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAlbumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = ActivityAddAlbumBinding.inflate(layoutInflater)
        binding.coordinator.transitionName = "shared_element_end_root"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildContainerTransform()
        window.sharedElementReturnTransition = buildContainerTransform()
        setContentView(binding.coordinator)
        super.onCreate(savedInstanceState)

        binding.btnAddAlbum.setOnClickListener {
            startIntent()
        }
    }



    /**
     * Open this Activity in Arc shape with animation
     */
    private fun buildContainerTransform() =
        MaterialContainerTransform().apply {
            addTarget(binding.coordinator)
            setAllContainerColors(MaterialColors.getColor(binding.root, R.attr.colorSurface))
            pathMotion = MaterialArcMotion()
            duration = 500
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN
        }

    private fun startIntent() {
        val intent = Intent(this, CardsActivity::class.java)
        startActivity(intent)
    }
}