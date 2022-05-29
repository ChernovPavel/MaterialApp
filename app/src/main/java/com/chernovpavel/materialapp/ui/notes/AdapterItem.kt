package com.chernovpavel.materialapp.ui.notes

sealed class AdapterItem {
    class NoteItem(val note: Note) : AdapterItem()
    class HeaderItem(val txt: String) : AdapterItem()
}

