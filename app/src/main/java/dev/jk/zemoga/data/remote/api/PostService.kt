package dev.jk.zemoga.data.remote.api

import dev.jk.zemoga.data.entities.CommentResponse
import dev.jk.zemoga.data.entities.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse>>

    @GET("posts/{id}/comments")
    suspend fun getCommentByPostId(@Path("id") postId: Int): Response<List<CommentResponse>>

}