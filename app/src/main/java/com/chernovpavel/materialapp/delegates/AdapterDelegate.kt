package com.chernovpavel.materialapp.delegates

import com.chernovpavel.materialapp.ui.notes.AdapterItem

interface AdapterDelegate<T> {

    val layoutId: Int

    fun canHandleType(item: AdapterItem): Boolean

    fun bind(item: T)

    fun created(holder: DelegatesViewHolder)
}