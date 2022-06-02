package com.chernovpavel.materialapp.delegates

import android.widget.ImageView
import com.chernovpavel.materialapp.R
import com.chernovpavel.materialapp.ui.notes.AdapterItem
import com.chernovpavel.materialapp.ui.notes.ImageItem

class ImageAdapterDelegate(private var itemLongClicked: () -> Unit) : AdapterDelegate<ImageItem> {

    private lateinit var img: ImageView

    override val layoutId: Int = R.layout.item_image

    override fun canHandleType(item: AdapterItem): Boolean = item is ImageItem

    override fun bind(item: ImageItem) {
        img.setImageResource(item.img)
    }

    override fun created(holder: DelegatesViewHolder) {
        img = holder.itemView.findViewById(R.id.img)

        img.setOnLongClickListener {
            itemLongClicked.invoke()
            true
        }
    }
}