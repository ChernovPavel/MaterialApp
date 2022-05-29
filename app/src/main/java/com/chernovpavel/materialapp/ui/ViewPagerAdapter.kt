package com.chernovpavel.materialapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chernovpavel.materialapp.ui.marsPhoto.MarsPhotoFragment
import com.chernovpavel.materialapp.ui.notes.NotesFragment
import com.chernovpavel.materialapp.ui.picOfDay.PicOfDayFragment
import com.chernovpavel.materialapp.ui.settings.SettingsFragment

private const val PICTURE_OF_THE_DAY_FRAGMENT = 0
private const val WEATHER_FRAGMENT = 1
private const val SETTINGS_FRAGMENT = 2


class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = arrayOf(PicOfDayFragment(), MarsPhotoFragment(), SettingsFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[PICTURE_OF_THE_DAY_FRAGMENT]
            1 -> fragments[WEATHER_FRAGMENT]
            2 -> fragments[SETTINGS_FRAGMENT]
            else -> fragments[PICTURE_OF_THE_DAY_FRAGMENT]
        }
    }
}