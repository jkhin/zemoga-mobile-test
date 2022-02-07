package dev.jk.zemoga.domain.entities

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var isFavorite: Boolean = false
)