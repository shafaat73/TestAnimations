package com.phaedra.myanimationsapplication.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.phaedra.myanimationsapplication.MainActivity
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.ui.ButtonsActivity

class DummyAdapter(private val activity: Activity,private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object: RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
        R.layout.cell, parent, false)){}

    override fun getItemCount() = 100

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
        startIntent(activity = activity,context)
        }
    }

    private fun startIntent(activity: Activity,context: Context) {
        val intent= Intent(activity,ButtonsActivity::class.java)
        context.startActivity(intent)
    }
}