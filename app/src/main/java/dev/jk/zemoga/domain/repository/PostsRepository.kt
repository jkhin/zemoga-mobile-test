package dev.jk.zemoga.domain.repository

import dev.jk.zemoga.data.entities.PostEntity
import dev.jk.zemoga.domain.entities.Comment
import dev.jk.zemoga.domain.entities.Post

interface PostsRepository {
    suspend fun getAllPosts(): List<Post>
    suspend fun getFavoritePosts(): List<Post>
    suspend fun getCommentsByPostId(postId: Int): List<Comment>
    suspend fun deleteAllPosts()
    suspend fun setPostAsFavorite(post: PostEntity)
}