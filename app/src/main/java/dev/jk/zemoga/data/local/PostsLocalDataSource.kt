package dev.jk.zemoga.data.local

import dev.jk.zemoga.data.entities.PostEntity
import javax.inject.Inject

class PostsLocalDataSource
@Inject constructor(
    private val postsDao: PostsDao
) {

    suspend fun getAllPosts(): List<PostEntity> = postsDao.getAllPosts()

    suspend fun getFavoritePosts(): List<PostEntity> = postsDao.getFavoritePosts()

    suspend fun saveAllPosts(entries: List<PostEntity>) = postsDao.insertAll(entries)

    suspend fun updatePost(entry: PostEntity) = postsDao.insert(entry)

    suspend fun deleteAllPosts() = postsDao.deleteAllPosts()

}