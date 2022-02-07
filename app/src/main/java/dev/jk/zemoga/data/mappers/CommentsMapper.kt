package dev.jk.zemoga.data.mappers

import dev.jk.zemoga.data.entities.CommentResponse
import dev.jk.zemoga.domain.entities.Comment
import dev.jk.zemoga.utils.Mapper
import javax.inject.Inject

class CommentsMapper
@Inject constructor() : Mapper<CommentResponse, Comment>() {
    override fun map(entry: CommentResponse): Comment {
        return Comment(
            postId = entry.postId,
            id = entry.id,
            name = entry.name,
            email = entry.email,
            body = entry.body
        )
    }
}