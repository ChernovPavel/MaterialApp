package com.example.materialapp.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.materialapp.R
import com.example.materialapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

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
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_image_search_24)
                1 -> tab.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_wb_sunny_24)
                2 -> tab.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_settings_24)
                else -> tab.icon =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_image_search_24)
            }
        }.attach()
    }
}