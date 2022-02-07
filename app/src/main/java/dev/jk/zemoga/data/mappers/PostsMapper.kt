package dev.jk.zemoga.data.mappers

import dev.jk.zemoga.data.entities.PostEntity
import dev.jk.zemoga.data.entities.PostResponse
import dev.jk.zemoga.domain.entities.Post
import dev.jk.zemoga.utils.Mapper
import javax.inject.Inject

class PostsMapper
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

    fun map(entry: PostEntity): Post {
        return Post(
            userId = entry.userId,
            id = entry.id,
            title = entry.title,
            body = entry.body,
            isFavorite = entry.isFavorite
        )
    }

}