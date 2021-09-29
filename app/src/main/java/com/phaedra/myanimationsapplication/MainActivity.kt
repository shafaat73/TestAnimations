package com.phaedra.myanimationsapplication

import android.animation.*
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager

import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.phaedra.myanimationsapplication.adapter.AlbumsAdapter
import com.phaedra.myanimationsapplication.databinding.ActivityMainBinding
import com.phaedra.myanimationsapplication.model.albums
import com.phaedra.myanimationsapplication.ui.AddAlbumActivity
import com.phaedra.myanimationsapplication.ui.AlbumDetailActivity
import com.phaedra.myanimationsapplication.utill.ButtonAnimation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * required for transitions and for using shared container transition
         */
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setClickListeners()
        setRecyclerView()


        val fadeAnim = ObjectAnimator.ofFloat(binding.btn, "alpha", 1f, 0f).apply {
            duration = 250
        }


        //for playing multiple animations
        // we can play animations together or after one by one
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(binding.addNoteFab, "translationY", -1000f, 0f),
            ObjectAnimator.ofFloat(binding.addNoteFab, "alpha", 0f, 1f),

        )
        //Interpolator is rate of the change of the animation.
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.duration = 2000
        
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                val animatorSet2 = AnimatorSet()
                animatorSet2.playTogether(

                    ObjectAnimator.ofFloat(binding.btn, "scaleX", 1f, 0.5f, 1f),
                    ObjectAnimator.ofFloat(binding.btn, "scaleY", 1f, 0.5f, 1f)
                )
                animatorSet2.interpolator = AccelerateInterpolator()
                animatorSet2.duration = 1000
                animatorSet2.start()
            }
        })






    }

    /**
     * this fun would expand the given view to given value
     */
    fun animateShapeExpand(view: View) {
        val anim = ValueAnimator.ofInt(view.measuredWidth, 1000)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.width = `val`
            view.requestLayout()
        }
        anim.duration = 300L
        anim.start()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animateShapeShrink(view)
            }
        })
    }
    /**
     * fading out the visibility of a view
     */
    fun fadeOutView(view: View) {
        view.animate()
            .alpha(1f)
            .setDuration(300)
            .start()
    }

    /**
     * this fun would shrink the given view to given value
     */
    fun animateShapeShrink(view: View) {
        val anim = ValueAnimator.ofInt(view.measuredWidth, view.measuredHeight)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.width = `val`
            view.requestLayout()
        }
        anim.duration = 300L
        anim.start()
    }

    /**
     * fading in the visibility of a view
     */
    fun onFadeInVisibleView(view: View, visibleView: View) {
        view.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    visibleView.visibility = View.VISIBLE
                }
            })
            .start()
    }
    private fun setRecyclerView() {
        val adapter = AlbumsAdapter(albums,this)
        adapter.albumClickListener = object : AlbumsAdapter.AlbumClickListener {
            override fun onAlbumClick(id: Int, albumCard: MaterialCardView) {
                val intent = Intent(this@MainActivity, AlbumDetailActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity, albumCard, id.toString()
                )
                intent.putExtra("albumId", id)
                startActivity(intent, options.toBundle())
            }
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = adapter
    }

    private fun setClickListeners() {
        binding.addNoteFab.setOnClickListener {
            val intent = Intent(this, AddAlbumActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, binding.addNoteFab, "shared_element_end_root"
            )
            startActivity(intent, options.toBundle())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }


}