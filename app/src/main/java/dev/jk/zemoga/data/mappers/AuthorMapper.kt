package dev.jk.zemoga.data.mappers

import dev.jk.zemoga.data.entities.AuthorResponse
import dev.jk.zemoga.domain.entities.Author
import dev.jk.zemoga.utils.Mapper
import javax.inject.Inject

class AuthorMapper
@Inject constructor() : Mapper<AuthorResponse, Author>() {

    override fun map(entry: AuthorResponse): Author {
        return Author(
            id = entry.id,
            name = entry.name,
            phone = entry.phone,
            email = entry.email,
            website = entry.website,
        )
    }
}