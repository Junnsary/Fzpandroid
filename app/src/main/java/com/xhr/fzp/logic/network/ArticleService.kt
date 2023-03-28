package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.Article
import com.xhr.fzp.logic.model.FzpResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {
    @GET("api/article")
    fun getArticleListByTag(@Query("tagid") tagId: String) : Call<FzpResponse<List<Article>>>

    @GET("api/article/{id}")
    fun getArticle(@Path("id") id: Int) : Call<FzpResponse<Article>>
}