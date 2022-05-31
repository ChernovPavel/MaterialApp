package com.chernovpavel.materialapp.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chernovpavel.materialapp.databinding.FragmentNoteItemBinding
import com.chernovpavel.materialapp.databinding.ItemHeaderBinding
import com.chernovpavel.materialapp.databinding.ItemImageBinding


class ListAdapter(
    private var noteItemClicked: ((txt: String) -> Unit)? = null,
    private var imgItemClicked: ((img: ImageItem) -> Unit)? = null
) : ListAdapter<AdapterItem, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<AdapterItem>() {

    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
        oldItem == newItem

}) {

    companion object {
        private const val TYPE_NOTE = 1
        private const val TYPE_HEADER = 2
        private const val TYPE_IMAGE = 3
    }

    override fun getItemViewType(position: Int): Int = when (currentList[position]) {
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
                val item = currentList[position] as NoteItem
                holder.title.text = item.text
            }

            is HeaderViewHolder -> {
                val item = currentList[position] as HeaderItem
                holder.headerTxt.text = item.txt
            }

            is ImageViewHolder -> {
                val item = currentList[position] as ImageItem
                holder.img.setImageResource(item.img)
            }
        }
    }


    inner class NoteViewHolder(binding: FragmentNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val title: TextView = binding.itemText

        init {
            itemView.setOnClickListener {
                (currentList[bindingAdapterPosition] as? NoteItem)?.let {
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
                (currentList[bindingAdapterPosition] as? ImageItem)?.let {
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