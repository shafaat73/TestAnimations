package com.phaedra.myanimationsapplication.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.adapter.DummyAdapter
import com.phaedra.myanimationsapplication.databinding.ActivityCardsBinding
import com.phaedra.myanimationsapplication.databinding.ActivityYoutubeAnimBinding

class YoutubeAnimActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var binding: ActivityYoutubeAnimBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeAnimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView=findViewById(R.id.recyclerView1)
        recyclerView.adapter = DummyAdapter(this,this)
        recyclerView.layoutManager = LinearLayoutManager(this)


    }


}