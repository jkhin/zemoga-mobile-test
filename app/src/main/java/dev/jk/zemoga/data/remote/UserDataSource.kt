package dev.jk.zemoga.data.remote

import dev.jk.zemoga.data.entities.AuthorResponse

interface UserDataSource {

    suspend fun getAuthor(userId: Int): AuthorResponse

}