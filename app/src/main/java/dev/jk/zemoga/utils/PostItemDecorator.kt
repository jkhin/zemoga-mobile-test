package dev.jk.zemoga.utils

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setItemDecorator() {
    val decorator = DividerItemDecoration(
        this.context,
        RecyclerView.VERTICAL
    )
    addItemDecoration(decorator)
}