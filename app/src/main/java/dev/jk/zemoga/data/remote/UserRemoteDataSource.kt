package dev.jk.zemoga.data.remote

import dev.jk.zemoga.data.entities.AuthorResponse
import dev.jk.zemoga.data.remote.api.UserService
import dev.jk.zemoga.data.remote.base.BaseDataSource
import javax.inject.Inject

class UserRemoteDataSource
@Inject constructor(
    private val userService: UserService
): UserDataSource, BaseDataSource(){
    override suspend fun getAuthor(userId: Int): AuthorResponse = getResult {
        userService.getAuthor(userId = userId)
    }
}
