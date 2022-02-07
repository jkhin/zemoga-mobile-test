package dev.jk.zemoga.data.remote

import dev.jk.zemoga.data.entities.CommentResponse
import dev.jk.zemoga.data.entities.PostResponse

interface PostDataSource {
    suspend fun getPosts(): List<PostResponse>
    suspend fun getCommentsByPostId(postId: Int): List<CommentResponse>
}