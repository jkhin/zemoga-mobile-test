package dev.jk.zemoga.ui.posts.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var isFavorite: Boolean = false
): Parcelable