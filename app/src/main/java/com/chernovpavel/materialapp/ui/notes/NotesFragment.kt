package com.chernovpavel.materialapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chernovpavel.materialapp.databinding.FragmentNotesListBinding


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val adapter = NoteRecyclerViewAdapter {

    }.apply {
        setData(
            listOf(
                Pair(AdapterItem.HeaderItem("NOTES"), false),
                Pair(AdapterItem.NoteItem(Note("Новая заметка 1", "Введите текст...")), false),
                Pair(AdapterItem.NoteItem(Note("Новая заметка 2", "Введите текст...")), false)
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