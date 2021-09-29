package com.phaedra.myanimationsapplication.ui

import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.adapter.ViewPagerAdapter
import com.phaedra.myanimationsapplication.databinding.ActivityViewPagerBinding
import com.phaedra.myanimationsapplication.utill.HorizontalMarginItemDecoration
import kotlin.math.abs


class ViewPagerActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewPagerBinding
    var positionPage=0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val list: MutableList<String> = ArrayList()
        list.add("First Screen")
        list.add("Second Screen")
        list.add("Third Screen")
        list.add("Fourth Screen")

        // You need to retain one page on each side so that the next and previous items are visible
        binding.viewPager.offscreenPageLimit = 1

// Add a PageTransformer that translates the next and previous items horizontally
// towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->

            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.35f * kotlin.math.abs(position))
            // If you want a fading effect uncomment the next line:
            page.alpha = 0.25f + (1 - abs(position))
            page.animate()

            //positionPage=position
            // View transformation
            val view=page.findViewById<LinearLayout>(R.id.fadingContainer)
            val img=page.findViewById<ImageView>(R.id.ivIcon)

            if(position <= -1.0F || position >= 1.0F) {
                view.alpha = 0.0F
                img.alpha = 0.0F

            } else if( position == 0.0F ) {
                view.alpha = 1.0F
                img.alpha = 1.0F
            } else { // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.alpha = 1.0F - abs(position) *1.6f
                img.alpha = 1.0F - abs(position) *1.6f
            }


            if (position ==-1.0f || position<0f){
                view.translationY = page.height/-2  * position;
                view.translationX=page.width/2*position
                img.translationY = page.height/-2 * position;
                img.translationX=page.width/2*position
            }else if (position ==0.0f || position<=1.0f){
                view.translationY = page.height/2  * position;
                view.translationX=page.width/2*position
                img.translationY = page.height/2 * position;
                img.translationX=page.width/2*position


            }


        }
        binding.viewPager.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            this,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        val adapter=ViewPagerAdapter(this,list,binding.viewPager,this)
        binding.viewPager.addItemDecoration(itemDecoration)
        binding.viewPager.adapter=adapter
        binding.viewPager.focusedChild


    }


}