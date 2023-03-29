package com.xhr.fzp.logic.network

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
    private val articleService = FzpServiceCreator.create<ArticleService>()
    private val videoService = FzpServiceCreator.create<VideoService>()
    private val tagService = FzpServiceCreator.create<TagService>()
    private val sourceService = FzpServiceCreator.create<SourceService>()
    private val userService = FzpServiceCreator.create<UserService>()
    private val imageService = FzpServiceCreator.create<ImageService>()
    private val commentService = FzpServiceCreator.create<CommentService>()
    private val collectionService = FzpServiceCreator.create<CollectionService>()


    /**
     * 获取api接口数据
     */
    suspend fun getArticleListByTag(tagId: String) =
        articleService.getArticleListByTag(tagId).await()

    suspend fun getArticle(id: Int) = articleService.getArticle(id).await()
    suspend fun getVideoListByTag(tagId: Int) = videoService.getVideoListByTag(tagId).await()
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
    suspend fun isUserCollect(collectionData: DetailViewModel.CollectionData)
    = collectionService.isUserCollect(collectionData.sourceId, collectionData.tagId, collectionData.userId).await()

    suspend fun addUserCollection(collectionData: DetailViewModel.CollectionData)
    = collectionService.addUserCollection(collectionData.sourceId, collectionData.tagId, collectionData.userId).await()

    suspend fun cancelUserCollection(collectionData: DetailViewModel.CollectionData)
    = collectionService.cancelUserCollection(collectionData.sourceId, collectionData.tagId, collectionData.userId).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }




}