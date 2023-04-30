package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.Answer
import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Message
import retrofit2.Call
import retrofit2.http.*

interface AnswerService  {

    @GET("api/answer/")
    fun getAnswerList(@Query("questionid") questionId: Int) : Call<FzpResponse<List<Answer>>>

    @FormUrlEncoded
    @POST("api/answer/")
    fun addAnswer(@Field("content") content: String, @Field("questionid") questionId: Int, @Field("date") date: String, @Field("userid") userId: String) : Call<FzpResponse<Message>>

}