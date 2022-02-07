package dev.jk.zemoga.ui.postdetail.mappers

import dev.jk.zemoga.domain.entities.Comment
import dev.jk.zemoga.ui.postdetail.models.CommentModel
import dev.jk.zemoga.utils.Mapper
import javax.inject.Inject

class CommentModelMapper
@Inject constructor() : Mapper<Comment, CommentModel>() {
    override fun map(entry: Comment): CommentModel {
        return CommentModel(
            postId = entry.postId,
            id = entry.id,
            name = entry.name,
            email = entry.email,
            body = entry.body
        )
    }
}