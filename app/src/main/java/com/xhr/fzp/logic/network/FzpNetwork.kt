package com.xhr.fzp.logic.network

import com.google.android.exoplayer2.drm.KeysExpiredException
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.ui.detail.DetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FzpNetwork {

    /**
     * 获取动态动态代理对象
     */
    private val videoService = FzpServiceCreator.create<VideoService>()
    private val tagService = FzpServiceCreator.create<TagService>()
    private val sourceService = FzpServiceCreator.create<SourceService>()
    private val userService = FzpServiceCreator.create<UserService>()
    private val imageService = FzpServiceCreator.create<ImageService>()
    private val commentService = FzpServiceCreator.create<CommentService>()
    private val favoritesService = FzpServiceCreator.create<FavoritesService>()


    /**
     * 获取api接口数据
     */
    suspend fun getTags(type: String, category: String) = tagService.getTags(type, category).await()
    suspend fun getKnowledge() = tagService.getKnowledge().await()
    suspend fun getSourceList(type: String, tagId: String) =
        sourceService.getSourceList(type, tagId).await()

    suspend fun getRecommList(num: Int) = sourceService.getRecommList(num).await()
    suspend fun userSignup(user: User) =
        userService.userSignup(user.id, user.email, user.name, user.passwd).await()

    suspend fun userLogin(user: User) = userService.userLogin(user.id, user.passwd).await()
    suspend fun getAvatar(fileName: String) = imageService.getAvatar(fileName).await()
    suspend fun getCommentList(sourceId: Int, tagId: Int) =
        commentService.getCommentList(sourceId, tagId).await()

    suspend fun getVideoInfo(sourceId: Int) = videoService.getVideoInfo(sourceId).await()
    suspend fun isUserCollect(favoritesData: DetailViewModel.FavoritesData) =
        favoritesService.isUserCollect(
            favoritesData.sourceId,
            favoritesData.tagId,
            favoritesData.userId
        ).await()

    suspend fun addUserFavorites(favoritesData: DetailViewModel.FavoritesData) =
        favoritesService.addUserFavorites(
            favoritesData.sourceId,
            favoritesData.tagId,
            favoritesData.userId
        ).await()

    suspend fun cancelUserFavorites(favoritesData: DetailViewModel.FavoritesData) =
        favoritesService.cancelUserFavorites(
            favoritesData.sourceId,
            favoritesData.tagId,
            favoritesData.userId
        ).await()

    suspend fun addUserComment(commentData: DetailViewModel.CommentData) =
        commentService.addUserComment(
            commentData.sourceId,
            commentData.tagId,
            commentData.userId,
            commentData.content
        ).await()

    suspend fun getFavoritesList(userId: String) = favoritesService.getFavoritesList(userId).await()

    suspend fun getSearchList(keywords: String) = sourceService.getSearchList(keywords).await()

    private suspend fun <T> Call<T>.await() = suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                //把body作为数据 发送出去
                val body = response.body()
                if (body != null)
                    continuation.resume(body)
                else
                    continuation.resumeWithException(RuntimeException("response body is null"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}