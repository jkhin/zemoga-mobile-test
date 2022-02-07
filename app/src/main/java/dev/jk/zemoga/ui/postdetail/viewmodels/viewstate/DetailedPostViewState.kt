package dev.jk.zemoga.ui.postdetail.viewmodels.viewstate

import dev.jk.zemoga.ui.postdetail.models.AuthorModel
import dev.jk.zemoga.ui.postdetail.models.CommentModel

sealed class DetailedPostViewState {
    class Loading(val isLoading: Boolean): DetailedPostViewState()
    class Success(val data: Pair<List<CommentModel>, AuthorModel>): DetailedPostViewState()
    class Error(val message: String): DetailedPostViewState()
}
