package com.chernovpavel.materialapp.delegates

import android.widget.TextView
import com.chernovpavel.materialapp.R
import com.chernovpavel.materialapp.ui.notes.AdapterItem
import com.chernovpavel.materialapp.ui.notes.NumberItem


class NumberAdapterDelegate : AdapterDelegate<NumberItem> {

    private lateinit var title: TextView

    override val layoutId: Int = R.layout.item_number

    override fun canHandleType(item: AdapterItem): Boolean = item is NumberItem

    override fun bind(item: NumberItem) {
        title.text = item.text
    }

    override fun created(holder: DelegatesViewHolder) {
        val itemView = holder.itemView

        title = itemView.findViewById(R.id.item_text)
    }
}