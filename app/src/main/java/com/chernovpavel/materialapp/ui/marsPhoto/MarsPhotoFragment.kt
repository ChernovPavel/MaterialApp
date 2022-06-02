package com.chernovpavel.materialapp.ui.marsPhoto

import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.chernovpavel.materialapp.databinding.FragmentMarsPhotoBinding
import com.chernovpavel.materialapp.domain.NasaRepositoryImpl
import com.chernovpavel.materialapp.ui.AnimationActivity

class MarsPhotoFragment : Fragment() {

    private val viewModel: MarsPhotoViewModel by viewModels {
        MarsPhotoViewModelFactory(
            NasaRepositoryImpl()
        )
    }

    private var _binding: FragmentMarsPhotoBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = false

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

        binding.imageView.setOnClickListener {
            isExpanded = isExpanded.not()
            TransitionManager.beginDelayedTransition(
                binding.marsPhotoRoot, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams

            if (isExpanded) {
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding.photoMarsButton.visibility = View.GONE
            } else {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.photoMarsButton.visibility = View.VISIBLE
            }
            binding.imageView.layoutParams = params
            binding.imageView.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else
                    ImageView.ScaleType.FIT_CENTER
        }

        binding.photoMarsButton.setOnClickListener {
            val intent = Intent(requireContext(), AnimationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
