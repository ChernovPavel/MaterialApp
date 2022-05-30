package com.chernovpavel.materialapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chernovpavel.materialapp.databinding.FragmentNotesListBinding


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val adapter = NoteRecyclerViewAdapter {
        Toast.makeText(
            requireActivity(),
            it.text,
            Toast.LENGTH_SHORT
        ).show()
    }.apply {
        setData(
            listOf(
                Pair(AdapterItem.HeaderItem("Это header"), false),
                Pair(AdapterItem.NoteItem(Note("один", "11 много текста много текста")), false),
                Pair(AdapterItem.NoteItem(Note("два", "22 много текста много текста")), false),
                Pair(AdapterItem.NoteItem(Note("три", "33 много текста много текста")), false)
            )
        )
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

        binding.recyclerActivityFAB.setOnClickListener { adapter.appendItem() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}