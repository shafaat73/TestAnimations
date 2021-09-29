package com.phaedra.myanimationsapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.phaedra.myanimationsapplication.R
import com.phaedra.myanimationsapplication.adapter.DummyAdapter
import com.phaedra.myanimationsapplication.databinding.ActivityAlbumDetailBinding
import com.phaedra.myanimationsapplication.databinding.ActivityMotionBinding

class MotionActivity : AppCompatActivity() {
    lateinit var binding:ActivityMotionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = DummyAdapter(this,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}