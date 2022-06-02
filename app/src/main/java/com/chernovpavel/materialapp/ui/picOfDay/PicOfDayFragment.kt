package com.chernovpavel.materialapp.ui.picOfDay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.chernovpavel.materialapp.R
import com.chernovpavel.materialapp.databinding.FragmentPicOfDayStartBinding
import com.chernovpavel.materialapp.domain.NasaRepositoryImpl

class PicOfDayFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(NasaRepositoryImpl()) }

    private var _binding: FragmentPicOfDayStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPicOfDayStartBinding.inflate(inflater, container, false)
        viewModel.requestPictureOfTheDay()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bsDescription = view.findViewById<TextView>(R.id.bottom_sheet_description)
        val bsDescriptionHeader = view.findViewById<TextView>(R.id.bottom_sheet_description_header)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

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
                    binding.imageCollapsingToolbar.load(response.url)
                    bsDescription?.text = response.explanation
                    bsDescriptionHeader?.text = response.title
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}