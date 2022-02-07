package dev.jk.zemoga.domain.usecases

import dev.jk.zemoga.data.entities.PostEntity
import dev.jk.zemoga.data.entities.PostResponse
import dev.jk.zemoga.domain.entities.Post
import dev.jk.zemoga.domain.repository.PostsRepository
import javax.inject.Inject

class PostsUseCase
@Inject constructor(
    private val postsRepository: PostsRepository
) {

    suspend fun getAllPosts() =
        postsRepository.getAllPosts()

    suspend fun getFavoritePosts() =
        postsRepository.getFavoritePosts()

    suspend fun getCommentsByPostId(postId: Int) =
        postsRepository.getCommentsByPostId(postId = postId)

    suspend fun deleteAllPosts() =
        postsRepository.deleteAllPosts()

    suspend fun setPostAsFavorite(post: Post) {
        val entity = createEntity(post)
        postsRepository.setPostAsFavorite(entity)
    }

    private fun createEntity(post: Post) = PostEntity(
        id = post.id,
        userId = post.userId,
        title = post.title,
        body = post.body,
        isFavorite = post.isFavorite
    )

}