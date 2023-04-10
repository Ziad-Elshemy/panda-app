package com.easy_pro_code.panda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easy_pro_code.panda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        val intentFragment = intent.extras?.getBoolean("fragmentToLoad")

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}