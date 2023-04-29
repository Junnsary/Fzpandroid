package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Source
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SourceService {
    @GET("api/sources/{type}/{tagid}")
    fun getSourceList(@Path("type") type: String, @Path("tagid") tagId: String) : Call<FzpResponse<List<Source>>>

    @GET("api/sources/recommendation")
    fun getRecommList(@Query("num") type: Int) : Call<FzpResponse<List<Source>>>
    @GET("api/sources/search")
    fun getSearchList(@Query("keywords") keywords: String) : Call<FzpResponse<List<Source>>>

}