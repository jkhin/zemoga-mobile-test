package dev.jk.zemoga.data.mappers

import dev.jk.zemoga.data.entities.PostEntity
import dev.jk.zemoga.data.entities.PostResponse
import dev.jk.zemoga.utils.Mapper
import javax.inject.Inject

class PostEntityMapper
@Inject constructor() :
    Mapper<PostResponse, PostEntity>() {

    override fun map(entry: PostResponse): PostEntity {
        return PostEntity(
            userId = entry.userId,
            id = entry.id,
            title = entry.title,
            body = entry.body,
            isFavorite = entry.isFavorite
        )
    }

}