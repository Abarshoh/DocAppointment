package com.example.doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doc.databinding.ActivityMainBinding
import com.example.doc.fragments.SplashFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}


