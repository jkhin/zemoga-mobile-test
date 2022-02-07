package dev.jk.zemoga.domain.repository

import dev.jk.zemoga.domain.entities.Author

interface AuthorRepository {
    suspend fun getAuthor(userId: Int): Author
}