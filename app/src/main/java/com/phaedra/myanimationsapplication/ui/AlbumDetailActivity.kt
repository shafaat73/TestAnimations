package com.phaedra.myanimationsapplication.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.databinding.ActivityAlbumDetailBinding
import com.phaedra.myanimationsapplication.model.albums

class AlbumDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        val noteId = intent.getIntExtra("albumId", 0)
        binding.coordinator.transitionName = noteId.toString()
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildContainerTransform()
        window.sharedElementReturnTransition = buildContainerTransform()
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        val album = albums.find { it.id == noteId }
        binding.album = album
        Glide.with(this)
            .asBitmap()
            .load(album?.image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val palette = Palette.from(resource).generate()
                    palette.darkVibrantSwatch?.let {
                        binding.collapsingToolbar.setBackgroundColor(it.rgb)
                        val color = getColor(R.color.black)
                        binding.collapsingToolbar.setCollapsedTitleTextColor(color)
                        binding.collapsingToolbar.setExpandedTitleColor(color)
                    } ?: palette.lightVibrantSwatch?.let {
                        binding.collapsingToolbar.setBackgroundColor(it.rgb)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

    }


    private fun buildContainerTransform() =
        MaterialContainerTransform().apply {
            addTarget(binding.coordinator)
            duration = 300
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN
        }
}