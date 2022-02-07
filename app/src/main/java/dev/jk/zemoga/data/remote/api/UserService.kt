package dev.jk.zemoga.data.remote.api

import dev.jk.zemoga.data.entities.AuthorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{id}")
    suspend fun getAuthor(@Path("id") userId: Int): Response<AuthorResponse>

}