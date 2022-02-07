package dev.jk.zemoga.ui.posts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jk.zemoga.domain.entities.Post
import dev.jk.zemoga.domain.usecases.PostsUseCase
import dev.jk.zemoga.ui.posts.models.PostModel
import dev.jk.zemoga.ui.posts.mappers.PostsModelMapper
import dev.jk.zemoga.ui.posts.viewmodels.viewstate.PostsViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel
@Inject constructor(
    private val postsUseCase: PostsUseCase,
    private val postsModelMapper: PostsModelMapper
) : ViewModel() {

    val state: LiveData<PostsViewState> get() = _state

    private val _state: MutableLiveData<PostsViewState> = MutableLiveData<PostsViewState>()

    fun getAllPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = postsUseCase.getAllPosts()
            val postsMapped = response.map { postsModelMapper.map(it) }
            launch(Dispatchers.Main) {
                emmitSuccessState(postsMapped)
            }
        }
    }

    fun setPostAsFavorite(model: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = createPostEntity(model)
            postsUseCase.setPostAsFavorite(entity)
        }
    }

    fun getFavoritePosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = postsUseCase.getFavoritePosts()
            val postsMapped = response.map { postsModelMapper.map(it) }

            launch(Dispatchers.Main) {
                emmitSuccessState(postsMapped)
            }
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postsUseCase.deleteAllPosts()

            launch(Dispatchers.Main) {
                emmitEmptyState()
            }
        }
    }

    private fun createPostEntity(model: PostModel) = Post(
        id = model.id,
        userId = model.userId,
        body = model.body,
        title = model.title,
        isFavorite = model.isFavorite
    )

    private fun emmitSuccessState(items: List<PostModel>) {
        val successState = PostsViewState.Success(data = items)
        _state.value = successState
    }

    private fun emmitEmptyState() {
        val errorState = PostsViewState.Empty()
        _state.value = errorState
    }

    private fun emmitErrorState(message: String) {
        val errorState = PostsViewState.Error(message = message)
        _state.value = errorState
    }

}