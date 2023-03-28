package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.Comment
import com.xhr.fzp.logic.model.FzpResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentService {

    @GET("api/comment")
    fun getCommentList(@Query("sourceid") sourceId: Int, @Query("tagid") tagId: Int) : Call<FzpResponse<List<Comment>>>


}