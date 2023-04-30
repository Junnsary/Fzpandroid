package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Message
import com.xhr.fzp.logic.model.Question
import retrofit2.Call
import retrofit2.http.*

interface QuestionService {
    @FormUrlEncoded
    @POST("api/question/")
    fun sendAskQuestion(@Field("userid") userId: String, @Field("content") content: String) : Call<FzpResponse<Message>>

    @GET("api/question/")
    fun getQuestionList(@Query("userid") userId: String, @Query("review") review: Int) : Call<FzpResponse<List<Question>>>

}