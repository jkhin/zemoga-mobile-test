package dev.jk.zemoga.ui.posts.mappers

import dev.jk.zemoga.domain.entities.Post
import dev.jk.zemoga.ui.posts.models.PostModel
import dev.jk.zemoga.utils.Mapper
import javax.inject.Inject

class PostsModelMapper
@Inject constructor() :
    Mapper<Post, PostModel>() {
    override fun map(entry: Post): PostModel {
        return PostModel(
            id = entry.id,
            body = entry.body,
            title = entry.title,
            userId = entry.userId,
            isFavorite = entry.isFavorite
        )
    }
}