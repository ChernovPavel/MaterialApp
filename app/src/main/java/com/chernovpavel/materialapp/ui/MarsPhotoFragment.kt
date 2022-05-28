package com.chernovpavel.materialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.chernovpavel.materialapp.databinding.FragmentMarsPhotoBinding
import com.chernovpavel.materialapp.domain.NasaRepositoryImpl

class MarsPhotoFragment : Fragment() {

    private val viewModel: MarsPhotoViewModel by viewModels {
        MarsPhotoViewModelFactory(
            NasaRepositoryImpl()
        )
    }

    private var _binding: FragmentMarsPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsPhotoBinding.inflate(inflater, container, false)
        viewModel.requestMarsPhoto()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.loading.collect {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.response.collect {
                it?.let { response ->
                    binding.imageView.load(response.photos?.get(0)?.imgSrc)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}