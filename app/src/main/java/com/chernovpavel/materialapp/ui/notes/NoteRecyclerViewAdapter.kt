package com.chernovpavel.materialapp.ui.notes

import android.view.LayoutInflater
import android.view.View
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

    private var data = mutableListOf<Pair<AdapterItem, Boolean>>()


    fun setData(notes: List<Pair<AdapterItem, Boolean>>) {
        data.apply {
            clear()
            addAll(notes)
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() =
        Pair(AdapterItem.NoteItem(Note("Введите заголовок", "Описание...")), false)


    override fun getItemViewType(position: Int): Int = when (data[position].first) {
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
                val item = data[position].first as AdapterItem.NoteItem
                holder.title.text = item.note.title
                holder.description.text = item.note.text
                holder.description.visibility =
                    if (data[position].second) View.VISIBLE else View.GONE
            }

            is HeaderViewHolder -> {
                val item = data[position].first as AdapterItem.HeaderItem
                holder.headerTxt.text = item.txt
            }
        }
    }

    override fun getItemCount(): Int = data.size

    inner class NoteViewHolder(binding: FragmentNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.itemTitle
        val description: TextView = binding.itemText

        init {
            itemView.setOnClickListener {
                (data[adapterPosition].first as? AdapterItem.NoteItem)?.let {
                    noteItemClicked?.invoke(it.note)
                }
            }

            binding.moveItemDown.setOnClickListener { moveDown() }
            binding.moveItemUp.setOnClickListener { moveUp() }

            binding.expandItem.setOnClickListener {
                toggleText(binding)
            }

            binding.removeItemImageView.setOnClickListener {
                removeItem()
            }
        }


        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun toggleText(binding: FragmentNoteItemBinding) {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            val txt = binding.itemTitle.text
            val desc = binding.itemText.text
            data[layoutPosition] =
                Pair(AdapterItem.NoteItem(Note("$txt", "$desc")), data[layoutPosition].second)
            notifyItemChanged(layoutPosition)
        }
    }


    inner class HeaderViewHolder(binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val headerTxt: TextView = binding.header
    }
}