package com.chernovpavel.materialapp.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chernovpavel.materialapp.databinding.FragmentNoteItemBinding
import com.chernovpavel.materialapp.databinding.HeaderItemBinding


class NoteRecyclerViewAdapter(
    private var noteItemClicked: ((note: Note) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_NOTE = 1
        private const val TYPE_HEADER = 2
    }

    private var data = mutableListOf<AdapterItem>()


    fun setData(notes: List<AdapterItem>) {
        data.apply {
            clear()
            addAll(notes)
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = AdapterItem.NoteItem(Note("новая заметка"))


    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is AdapterItem.NoteItem -> TYPE_NOTE
        is AdapterItem.HeaderItem -> TYPE_HEADER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_NOTE ->
                NoteViewHolder(
                    FragmentNoteItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            TYPE_HEADER ->
                HeaderViewHolder(
                    HeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            else -> throw IllegalStateException("Cannot be here")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> {
                val item = data[position] as AdapterItem.NoteItem
                holder.title.text = item.note.text
            }

            is HeaderViewHolder -> {
                val item = data[position] as AdapterItem.HeaderItem
                holder.headerTxt.text = item.txt
            }
        }
    }

    override fun getItemCount(): Int = data.size

    inner class NoteViewHolder(binding: FragmentNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.itemText

        init {
            itemView.setOnClickListener {
                (data[adapterPosition] as? AdapterItem.NoteItem)?.let {
                    noteItemClicked?.invoke(it.note)
                }
            }

            binding.removeItemImageView.setOnClickListener {
                removeItem()
            }
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }


    inner class HeaderViewHolder(binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val headerTxt: TextView = binding.header
    }
}