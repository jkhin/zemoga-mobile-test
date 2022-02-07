package dev.jk.zemoga.ui.postdetail.models

data class CommentModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)