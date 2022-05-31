package com.chernovpavel.materialapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.chernovpavel.materialapp.R
import com.chernovpavel.materialapp.databinding.FragmentNotesListBinding
import com.google.android.material.snackbar.Snackbar


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val adapter = NoteRecyclerViewAdapter(
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
        setData(
            listOf(
                HeaderItem("Это header"),
                NoteItem("один"),
                ImageItem(R.drawable.house),
                NoteItem("два"),
                NoteItem("три"),
                ImageItem(R.drawable.house)

            )
        )
        notifyDataSetChanged()
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

        ItemTouchHelper(ItemTouchHelperCallback {
            adapter.itemRemoved(it)
            adapter.notifyItemRemoved(it)
        })
            .attachToRecyclerView(binding.listRv)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}