package dev.jk.zemoga.data.remote

import dev.jk.zemoga.data.entities.CommentResponse
import dev.jk.zemoga.data.entities.PostResponse
import dev.jk.zemoga.data.remote.api.PostService
import dev.jk.zemoga.data.remote.base.BaseDataSource
import javax.inject.Inject

class PostsRemoteDataSource
@Inject constructor(
    private val postsService: PostService
) : PostDataSource, BaseDataSource() {

    override suspend fun getPosts(): List<PostResponse> = getResult {
        postsService.getPosts()
    }

    override suspend fun getCommentsByPostId(postId: Int): List<CommentResponse> = getResult {
        postsService.getCommentByPostId(postId = postId)
    }

}