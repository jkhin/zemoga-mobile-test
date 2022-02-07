package dev.jk.zemoga.data.local

import androidx.room.*
import dev.jk.zemoga.data.entities.PostEntity


@Dao
interface PostsDao {

    @Query("SELECT * FROM posts ORDER BY isFavorite DESC")
    suspend fun getAllPosts() : List<PostEntity>

    @Query("SELECT * FROM posts WHERE isFavorite = 1")
    suspend fun getFavoritePosts() : List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(characters: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: PostEntity)

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()

}