package com.oosca.parcial1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oosca.parcial1.R
import com.oosca.parcial1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}