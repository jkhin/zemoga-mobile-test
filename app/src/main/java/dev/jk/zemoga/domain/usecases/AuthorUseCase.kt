package dev.jk.zemoga.domain.usecases

import dev.jk.zemoga.domain.repository.AuthorRepository
import javax.inject.Inject

class AuthorUseCase
@Inject constructor(
    private val usersRepository: AuthorRepository
) {

    suspend fun getAuthor(userId: Int) = usersRepository.getAuthor(userId = userId)

}