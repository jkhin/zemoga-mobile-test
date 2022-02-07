package dev.jk.zemoga.data.entities

data class PostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var isFavorite: Boolean,
)