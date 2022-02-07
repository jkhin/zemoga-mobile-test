package dev.jk.zemoga.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jk.zemoga.data.local.AppDatabase
import dev.jk.zemoga.data.local.PostsLocalDataSource
import dev.jk.zemoga.data.mappers.AuthorMapper
import dev.jk.zemoga.data.mappers.CommentsMapper
import dev.jk.zemoga.data.mappers.PostsMapper
import dev.jk.zemoga.data.remote.PostDataSource
import dev.jk.zemoga.data.remote.PostsRemoteDataSource
import dev.jk.zemoga.data.remote.UserDataSource
import dev.jk.zemoga.data.remote.UserRemoteDataSource
import dev.jk.zemoga.data.remote.api.PostService
import dev.jk.zemoga.data.remote.api.UserService
import dev.jk.zemoga.data.repository.PostsRepositoryImpl
import dev.jk.zemoga.data.repository.AuthorRepositoryImpl
import dev.jk.zemoga.domain.repository.PostsRepository
import dev.jk.zemoga.domain.repository.AuthorRepository
import dev.jk.zemoga.ui.posts.mappers.PostsModelMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providesPostService(retrofit: Retrofit) = retrofit.create(PostService::class.java)

    @Provides
    fun providesUserService(retrofit: Retrofit) = retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun providesPostsMapper() = PostsMapper()

    @Singleton
    @Provides
    fun providesPostsModelMapper() = PostsModelMapper()

    @Singleton
    @Provides
    fun providesPostsDataSource(postService: PostService) =
        PostsRemoteDataSource(postsService = postService) as PostDataSource

    @Singleton
    @Provides
    fun providesUserDataSource(userService: UserService) =
        UserRemoteDataSource(userService = userService) as UserDataSource

    @Singleton
    @Provides
    fun providesPostsRepository(
        postDataSource: PostDataSource,
        postsLocalDataSource: PostsLocalDataSource,
        postsMapper: PostsMapper,
        commentsMapper: CommentsMapper
    ) =
        PostsRepositoryImpl(
            postsRemoteDataSource = postDataSource,
            postsLocalDataSource = postsLocalDataSource,
            postsMapper = postsMapper,
            commentsMapper = commentsMapper
        ) as PostsRepository

    @Singleton
    @Provides
    fun providesUserRepository(
        userDataSource: UserDataSource
    ) =
        AuthorRepositoryImpl(
            userDataSource = userDataSource,
            authorMapper = AuthorMapper()
        ) as AuthorRepository

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun providesPostsDao(db: AppDatabase) = db.postsDao()

}