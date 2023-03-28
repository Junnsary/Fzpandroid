package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Tag
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TagService {

    @GET("api/tags/{type}/{category}")
    fun getTags(@Path("type") type: String, @Path("category") category: String) : Call<FzpResponse<List<Tag>>>

    @GET("api/tags/knowledge")
    fun getKnowledge() : Call<FzpResponse<List<Tag>>>
}