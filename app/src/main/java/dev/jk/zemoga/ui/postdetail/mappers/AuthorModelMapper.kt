package dev.jk.zemoga.ui.postdetail.mappers

import dev.jk.zemoga.domain.entities.Author
import dev.jk.zemoga.ui.postdetail.models.AuthorModel
import dev.jk.zemoga.utils.Mapper
import javax.inject.Inject

class AuthorModelMapper
@Inject constructor() : Mapper<Author, AuthorModel>() {

    override fun map(entry: Author): AuthorModel {
        return AuthorModel(
            id = entry.id,
            name = entry.name,
            phone = entry.phone,
            email = entry.email,
            website = entry.website,
        )
    }
}