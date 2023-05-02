package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.UserTopic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserTopicService {

    @GET("/api/usertopic/list")
    fun getUserTopicList(@Query("userid") userId: String) : Call<FzpResponse<List<UserTopic>>>

}