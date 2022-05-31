package com.chernovpavel.materialapp.ui.notes

import androidx.annotation.DrawableRes

sealed class AdapterItem(val key: String)

data class NoteItem(val id: String, val text: String) : AdapterItem(id)
data class ImageItem(val id: String, @DrawableRes val img: Int) : AdapterItem(id)
data class HeaderItem(val id: String, val txt: String) : AdapterItem(id)