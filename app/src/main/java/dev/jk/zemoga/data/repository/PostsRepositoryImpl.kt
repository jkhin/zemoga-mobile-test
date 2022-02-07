package dev.jk.zemoga.data.repository

import dev.jk.zemoga.data.entities.PostEntity
import dev.jk.zemoga.data.local.PostsLocalDataSource
import dev.jk.zemoga.data.mappers.CommentsMapper
import dev.jk.zemoga.data.mappers.PostsMapper
import dev.jk.zemoga.data.remote.PostDataSource
import dev.jk.zemoga.domain.entities.Comment
import dev.jk.zemoga.domain.entities.Post
import dev.jk.zemoga.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl
@Inject constructor(
    private val postsRemoteDataSource: PostDataSource,
    private val postsLocalDataSource: PostsLocalDataSource,
    private val postsMapper: PostsMapper,
    private val commentsMapper: CommentsMapper
) : PostsRepository {

    override suspend fun getAllPosts(): List<Post> {
        val storagePosts = postsLocalDataSource.getAllPosts()

        return if (!storagePosts.isNullOrEmpty()) {
            storagePosts.map(postsMapper::map)
        } else {
            val response = postsRemoteDataSource.getPosts()
            val entities = response.map(postsMapper::map)
            postsLocalDataSource.saveAllPosts(entities)
            entities.map(postsMapper::map)
        }
    }

    override suspend fun getFavoritePosts(): List<Post> {
        return postsLocalDataSource.getFavoritePosts().map { postsMapper.map(it) }
    }

    override suspend fun setPostAsFavorite(post: PostEntity) {
        postsLocalDataSource.updatePost(entry = post)
    }

    override suspend fun getCommentsByPostId(postId: Int): List<Comment> {
        val response = postsRemoteDataSource.getCommentsByPostId(postId = postId)
        return response.map { commentsMapper.map(it) }
    }

    override suspend fun deleteAllPosts() = postsLocalDataSource.deleteAllPosts()

}