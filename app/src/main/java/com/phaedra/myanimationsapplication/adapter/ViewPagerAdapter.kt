package com.phaedra.myanimationsapplication.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.ui.ButtonsActivity
import com.phaedra.myanimationsapplication.ui.DribbleAnimActivity
import com.phaedra.myanimationsapplication.ui.YoutubeAnimActivity

class ViewPagerAdapter internal constructor(
    private val context: Context?,
    data: List<String>,
    viewPager2: ViewPager2,
   private val activity: Activity
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    private var lastPosition = -1

    private val mData: List<String>
    private val mInflater = LayoutInflater.from(context)
    private val viewPager2: ViewPager2
    private val colorArray = intArrayOf(
        android.R.color.black,
        android.R.color.holo_blue_dark,
        android.R.color.holo_green_dark,
        android.R.color.holo_red_dark
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_viewpager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = mData[position]
        holder.myTextView.text = animal
        holder.relativeLayout.setBackgroundResource(colorArray[position])

        holder.itemView.setOnClickListener {
            startIntent(activity = activity,context =context!! )
        }
    }



    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var myTextView: TextView
        var relativeLayout: LinearLayout


        init {
            myTextView = itemView.findViewById(R.id.tvTitle)
            relativeLayout = itemView.findViewById(R.id.container)

        }
    }



    private fun startIntent(activity: Activity,context: Context) {
        val intent= Intent(activity, DribbleAnimActivity::class.java)
        context.startActivity(intent)
    }

    init {
        mData = data
        this.viewPager2 = viewPager2
    }
}