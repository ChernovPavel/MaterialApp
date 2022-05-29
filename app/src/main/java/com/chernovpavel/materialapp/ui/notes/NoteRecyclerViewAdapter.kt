package com.chernovpavel.materialapp.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chernovpavel.materialapp.databinding.FragmentNoteItemBinding


class NoteRecyclerViewAdapter(
    private var itemClicked: ((note: Note) -> Unit)? = null
) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    var notesData = mutableListOf<Note>()


    fun setData(notes: List<Note>) {
        notesData.apply {
            clear()
            addAll(notes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notesData[position]
        holder.title.text = item.text
    }

    override fun getItemCount(): Int = notesData.size

    inner class ViewHolder(binding: FragmentNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.itemTitle

        init {
            itemView.setOnClickListener {
                itemClicked?.invoke(notesData[adapterPosition])
            }
        }
    }

}