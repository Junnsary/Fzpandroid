package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.Comment
import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Message
import retrofit2.Call
import retrofit2.http.*

interface CommentService {

    @GET("api/comment")
    fun getCommentList(@Query("sourceid") sourceId: Int, @Query("tagid") tagId: Int) : Call<FzpResponse<List<Comment>>>

    @FormUrlEncoded
    @POST("api/comment")
    fun addUserComment(@Field("sourceid") sourceId: Int, @Field("tagid") tagId: Int, @Field("userid") userId: String, @Field("content") content: String) : Call<FzpResponse<Message>>

}