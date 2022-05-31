package com.chernovpavel.materialapp.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chernovpavel.materialapp.databinding.FragmentNoteItemBinding
import com.chernovpavel.materialapp.databinding.ItemHeaderBinding
import com.chernovpavel.materialapp.databinding.ItemImageBinding


class NoteRecyclerViewAdapter(
    private var noteItemClicked: ((txt: String) -> Unit)? = null,
    private var imgItemClicked: ((img: ImageItem) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_NOTE = 1
        private const val TYPE_HEADER = 2
        private const val TYPE_IMAGE = 3
    }

    private var data = mutableListOf<AdapterItem>()


    fun setData(notes: List<AdapterItem>) {
        data.apply {
            clear()
            addAll(notes)
        }
    }

    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is NoteItem -> TYPE_NOTE
        is HeaderItem -> TYPE_HEADER
        is ImageItem -> TYPE_IMAGE
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
                    ItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            TYPE_IMAGE ->
                ImageViewHolder(
                    ItemImageBinding.inflate(
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
                val item = data[position] as NoteItem
                holder.title.text = item.text
            }

            is HeaderViewHolder -> {
                val item = data[position] as HeaderItem
                holder.headerTxt.text = item.txt
            }

            is ImageViewHolder -> {
                val item = data[position] as ImageItem
                holder.img.setImageResource(item.img)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun itemRemoved(pos: Int) {
        data.removeAt(pos)
    }

    inner class NoteViewHolder(binding: FragmentNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.itemText

        init {
            itemView.setOnClickListener {
                (data[bindingAdapterPosition] as? NoteItem)?.let {
                    noteItemClicked?.invoke(it.text)
                }
            }
        }
    }

    inner class ImageViewHolder(binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val img: ImageView = binding.img

        init {
            img.setOnLongClickListener {
                (data[bindingAdapterPosition] as? ImageItem)?.let {
                    imgItemClicked?.invoke(it)
                }
                true
            }
        }
    }

    inner class HeaderViewHolder(binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val headerTxt: TextView = binding.header
    }
}