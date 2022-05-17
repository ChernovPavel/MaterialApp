package com.example.materialapp.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.materialapp.R
import com.example.materialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val theme = getPreferences(Context.MODE_PRIVATE)
            .getInt(THEME, R.style.AppTheme_GreenTheme)
        setTheme(theme)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(this)
    }
}