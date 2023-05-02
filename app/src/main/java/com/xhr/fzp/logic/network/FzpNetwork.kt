package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.Answer
import com.xhr.fzp.logic.model.Question
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.ui.detail.DetailViewModel
import com.xhr.fzp.utils.formatDateTime
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
    private val questionService = FzpServiceCreator.create<QuestionService>()
    private val answerService = FzpServiceCreator.create<AnswerService>()
    private val userTopicService = FzpServiceCreator.create<UserTopicService>()


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

    suspend fun sendAskQuestion(question: Question) =
        questionService.sendAskQuestion(question.user.id, question.content).await()

    suspend fun getQuestionList(userId: String, review: Int) =
        questionService.getQuestionList(userId, review).await()

    suspend fun getAnswerList(questionId: Int) = answerService.getAnswerList(questionId).await()
    suspend fun addAnswer(answer: Answer) = answerService.addAnswer(
        answer.content, answer.questionId, formatDateTime(answer.createdAt), answer.user.id
    ).await()
    suspend fun getUserTopicList(userId: String) = userTopicService.getUserTopicList(userId).await()


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