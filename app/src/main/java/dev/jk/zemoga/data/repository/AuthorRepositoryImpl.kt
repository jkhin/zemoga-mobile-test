package dev.jk.zemoga.data.repository

import dev.jk.zemoga.data.mappers.AuthorMapper
import dev.jk.zemoga.data.remote.UserDataSource
import dev.jk.zemoga.domain.entities.Author
import dev.jk.zemoga.domain.repository.AuthorRepository
import javax.inject.Inject

class AuthorRepositoryImpl
@Inject constructor(
    private val userDataSource: UserDataSource,
    private val authorMapper: AuthorMapper
): AuthorRepository{

    override suspend fun getAuthor(userId: Int): Author {
        val response = userDataSource.getAuthor(userId = userId)
        return authorMapper.map(response)
    }

}