package com.example.materialapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.materialapp.R
import com.example.materialapp.databinding.FragmentMainBinding
import com.example.materialapp.domain.NasaRepositoryImpl

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels() { MainViewModelFactory(NasaRepositoryImpl()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.requestPictureOfTheDay()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainBinding.bind(view)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.loading.collect() {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error.collect() {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.image.collect() { url ->
                url?.let {
                    binding.img.load(it)
                }
            }
        }
    }
}