package com.example.materialapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.materialapp.R
import com.example.materialapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val binding = FragmentMainBinding.bind(view)
    }
}