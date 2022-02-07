package dev.jk.zemoga.ui.postdetail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jk.zemoga.domain.entities.Post
import dev.jk.zemoga.domain.usecases.AuthorUseCase
import dev.jk.zemoga.domain.usecases.PostsUseCase
import dev.jk.zemoga.ui.postdetail.models.AuthorModel
import dev.jk.zemoga.ui.postdetail.models.CommentModel
import dev.jk.zemoga.ui.posts.models.PostModel
import dev.jk.zemoga.ui.postdetail.mappers.AuthorModelMapper
import dev.jk.zemoga.ui.postdetail.mappers.CommentModelMapper
import dev.jk.zemoga.ui.postdetail.viewmodels.viewstate.DetailedPostViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailedPostViewModel
@Inject constructor(
    private val postsUseCase: PostsUseCase,
    private val authorUseCase: AuthorUseCase,
    private val commentModelMapper: CommentModelMapper,
    private val authorModelMapper: AuthorModelMapper
) : ViewModel() {

    val state: LiveData<DetailedPostViewState> get() = _state

    private val _state: MutableLiveData<DetailedPostViewState> =
        MutableLiveData<DetailedPostViewState>()

    fun getPostDetailedInfo(model: PostModel) {

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                emmitLoadingState(true)
            }

            val author = authorUseCase.getAuthor(userId = model.userId)
            val authorMapped = authorModelMapper.map(author)
            val comments = postsUseCase.getCommentsByPostId(postId = model.id)
            val commentsMapped = comments.map { commentModelMapper.map(it) }

            withContext(Dispatchers.Main) {
                emmitSuccessState(Pair(commentsMapped, authorMapped))
                emmitLoadingState(false)
            }

        }
    }

    fun setPostAsFavorite(model: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = createPostEntity(model)
            postsUseCase.setPostAsFavorite(post = entity)
        }
    }

    private fun createPostEntity(model: PostModel) = Post(
        id = model.id,
        userId = model.userId,
        body = model.body,
        title = model.title,
        isFavorite = model.isFavorite
    )

    private fun emmitSuccessState(pair: Pair<List<CommentModel>, AuthorModel>) {
        val successState = DetailedPostViewState.Success(data = pair)
        _state.value = successState
    }

    private fun emmitLoadingState(isLoading: Boolean) {
        val loadingState = DetailedPostViewState.Loading(isLoading = isLoading)
        _state.value = loadingState
    }

}