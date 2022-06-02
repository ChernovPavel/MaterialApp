package com.chernovpavel.materialapp.delegates

import android.widget.TextView
import com.chernovpavel.materialapp.R
import com.chernovpavel.materialapp.ui.notes.AdapterItem
import com.chernovpavel.materialapp.ui.notes.HeaderItem

class HeaderAdapterDelegate : AdapterDelegate<HeaderItem> {

    private lateinit var header: TextView

    override val layoutId: Int = R.layout.item_header

    override fun canHandleType(item: AdapterItem): Boolean = item is HeaderItem

    override fun bind(item: HeaderItem) {
        header.text = item.txt
    }

    override fun created(holder: DelegatesViewHolder) {
        val itemView = holder.itemView

        header = itemView.findViewById(R.id.header)
    }
}