package com.chernovpavel.materialapp.ui.notes

import androidx.annotation.DrawableRes

sealed class AdapterItem

class NoteItem(val text: String) : AdapterItem()
class ImageItem(@DrawableRes val img: Int) : AdapterItem()
class HeaderItem(val txt: String) : AdapterItem()