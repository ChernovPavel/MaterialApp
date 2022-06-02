package com.chernovpavel.materialapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.chernovpavel.materialapp.databinding.FragmentNotesListBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private var data = mutableListOf(
        NumberItem("id0", "ноль"),
        HeaderItem("id1", "header"),
        NumberItem("id2", "два"),
        ImageItem("id3", R.drawable.house),
        NumberItem("id4", "четыре"),
        NumberItem("id5", "пять")
    )

    private val adapter = ListAdapter(
        {
            Snackbar.make(
                binding.listRv,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        },
        {
            Snackbar.make(
                binding.listRv,
                it.img.toString(),
                Snackbar.LENGTH_SHORT
            ).show()
        }).apply {
        submitList(data as List<AdapterItem>?)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listRv.adapter = adapter

        ItemTouchHelper(
            ItemTouchHelperCallback(
                {
                    val copy = ArrayList(data)
                    copy.removeAt(it)
                    adapter.submitList(copy as List<AdapterItem>?)
                    data = copy

                },
                { from, to ->
                    val copy = ArrayList(data)
                    Collections.swap(copy, from, to)
                    adapter.submitList(copy as List<AdapterItem>?)
                    data = copy
                })
        )
            .attachToRecyclerView(binding.listRv)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}