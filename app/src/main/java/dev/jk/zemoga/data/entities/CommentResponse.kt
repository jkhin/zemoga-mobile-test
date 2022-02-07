package dev.jk.zemoga.data.entities

data class CommentResponse(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)