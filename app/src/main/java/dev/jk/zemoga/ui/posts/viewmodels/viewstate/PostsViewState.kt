package dev.jk.zemoga.ui.posts.viewmodels.viewstate

import dev.jk.zemoga.ui.posts.models.PostModel

sealed class PostsViewState {
    class Success(val data: List<PostModel>) : PostsViewState()
    class Empty: PostsViewState()
    class Error(val message: String) : PostsViewState()
}
